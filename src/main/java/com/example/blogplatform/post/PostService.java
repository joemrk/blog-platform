package com.example.blogplatform.post;

import com.example.blogplatform.category.Category;
import com.example.blogplatform.exception.errors.NotFoundException;
import com.example.blogplatform.fs.FileSystemService;
import com.example.blogplatform.post.dto.PostCreateDto;
import com.example.blogplatform.post.dto.PostResponse;
import com.example.blogplatform.posts_tags.PostsTags;
import com.example.blogplatform.posts_tags.PostsTagsRepository;
import com.example.blogplatform.score.ScoreService;
import com.example.blogplatform.tag.Tag;
import com.example.blogplatform.tag.TagService;
import com.example.blogplatform.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostService {
  private final PostRepository postRepository;
  private final FileSystemService fileSystemService;
  private final PostsTagsRepository postsTagsRepository;
  private final TagService tagsService;
  private final ScoreService scoreService;

  @Autowired
  public PostService(
    PostRepository postRepository, 
    FileSystemService fileSystemService, 
    PostsTagsRepository postsTagsRepository, 
    TagService tagsService,
    ScoreService scoreService) {
    this.postRepository = postRepository;
    this.fileSystemService = fileSystemService;
    this.postsTagsRepository = postsTagsRepository;
    this.tagsService = tagsService;
    this.scoreService = scoreService;
  }

  public List<PostResponse> findAll() {
    List<Post> posts =  postRepository.findAll();
    return joinScore(joinTags(posts));
  }

  public PostResponse findById(Long id) {
    Post exist =  postRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Post not found"));
    return joinScore(joinTags(List.of(exist))).get(0);
  }

  public List<PostResponse> findByTitle(String title) {
    return joinScore(joinTags(postRepository.findByTitleLikeIgnoreCase("%" + title + "%")));
  }

  public Post createOne(PostCreateDto dto, User current) {
    String imagePath = fileSystemService.uploadBase64ToSftp(dto.getImage().getBase64());
    Post create = Post.builder()
            .body(dto.getBody())
            .title(dto.getTitle())
            .user(User.builder().id(current.getId()).build())
            .category(Category.builder().id(dto.getCategoryId()).build())
            .image(imagePath)
            .build();

    Post newPost =  postRepository.save(create);

    List<PostsTags> postsTags = dto.getTags().stream().map(t ->
            PostsTags.builder().tagId(t.getId()).postId(newPost.getId()).build()).toList();

    postsTagsRepository.saveAll(postsTags);

    return newPost;
  }

  public void delete(Long id, User current) {
    Post exist = postRepository.findByIdAndUserId(id, current.getId());
    if(exist != null){
      postRepository.deleteById(id);
    }
  }

  public Post update(Post post, User current) {
    Post exist = postRepository.findByIdAndUserId(post.getId(), current.getId());
    if(exist == null){
      throw new NotFoundException("Post not found");
    }
    exist.setBody(post.getBody());
    exist.setTitle(post.getTitle());

    return postRepository.save(exist);
  }

  public List<PostResponse> findByUser(Long userId) {
    return joinScore(joinTags(postRepository.findByUserId(userId)));
  }

  public List<Post> findByCategory(Long userId) {
    return postRepository.findByCategoryId(userId);
  }

  private List<PostResponse> joinTags(List<Post> posts) {
    // cuz fucking many_to_many don't work
    Set<Long> postsIds = posts.stream()
            .map(Post::getId)
            .collect(Collectors.toSet());
    List<PostsTags> postsTags = postsTagsRepository.findByPostIdIn(postsIds);
    Set<Long> tagsIds = postsTags.stream()
            .map(PostsTags::getTagId)
            .collect(Collectors.toSet());

    Map<Long, Tag> tags = tagsService.findTagsIn(tagsIds);

    Map<Long, List<Tag>> tagsByPostId = postsTags.stream()
            .collect(Collectors.groupingBy(
                    PostsTags::getPostId,
            Collectors.mapping(pt -> tags.get(pt.getTagId()), Collectors.toList())));

    return posts.stream().map(p -> PostResponse.builder()
            .tags(tagsByPostId.get(p.getId()))
            .title(p.getTitle())
            .image(p.getImage())
            .body(p.getBody())
            .id(p.getId())
            .category(p.getCategory())
            .user(p.getUser())
            .createdAt(p.getCreatedAt())
            .updatedAt(p.getUpdatedAt())
            .build())
            .toList();
  }

  private List<PostResponse> joinScore(List<PostResponse> posts) {
    Set<Long> postsIds = posts.stream()
            .map(PostResponse::getId)
            .collect(Collectors.toSet());

    Map<Long, Integer> scores = scoreService.getPostScore(postsIds);
    
    return posts.stream().map(p -> {
      p.setScore(scores.get(p.getId()));
      return p;
    }).toList();
  }
}
