//package com.codersquare.filters;
//
//import com.codersquare.post.PostRepository;
//import com.github.javafaker.Faker;
//import lombok.AllArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//
//@Component
//@AllArgsConstructor
//public class SampleDataLoader implements CommandLineRunner {
//
//    private final PostRepository repository;
//    private final Faker faker;
//
//    @Override
//    public void run(String... args) throws Exception {
//
////        // Create 100 rows of posts
////        List<Post> posts = IntStream.rangeClosed(1,100)
////                .mapToObj(i -> new Post(
////                        new User(
////                                faker.name().firstName(),
////                                faker.name().lastName(),
////                                faker.name().username(),
////                                faker.internet().password(),
////                                faker.internet().emailAddress()
////                        ),
////                        faker.lordOfTheRings().character(),
////                        faker.leagueOfLegends().location(),
////                        faker.internet().url()
////
////                )).toList();
////
////        repository.saveAll(posts);
//
//
//    }
//}
