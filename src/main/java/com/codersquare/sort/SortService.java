package com.codersquare.sort;

import com.codersquare.mapper.PostDTOMapper;
import com.codersquare.post.PostRepository;
import com.codersquare.response.PostDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SortService {
    private final PostRepository postRepository;
    private final PostDTOMapper postDTOMapper;


    public Page<PostDTO> getPostsSortedByRecent(int page) {
        return postRepository.findByOrderByCreatedAtDesc(
                        PageRequest.of(page, 20, Sort.by("createdAt").descending()))
                .map(postDTOMapper);
    }


    public Page<PostDTO> getPostsSortedByPopularity(int page) {
        return postRepository.findByOrderByPointsDesc(
                        PageRequest.of(page, 20, Sort.by("points").descending()))
                .map(postDTOMapper);
    }


    public Page<PostDTO> getHotPosts(int page) {
        return postRepository.findHotPosts(
                PageRequest.of(page, 20)).map(postDTOMapper);
    }

}
