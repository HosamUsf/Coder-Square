---
# Coder square 


Coder square is a social web app designed to ignite your learning journey through a curated collection of resources, shared and voted on by your fellow developers. Think Hackernews meets learning platform, empowering you to discover new tools, frameworks, and tutorials, all within a vibrant community.


![image](https://github.com/HosamUsf/Coder-Square/assets/57178026/e7972c48-d2c3-47cc-847f-4ae315190ae5)

## Table of Contents
- [Intro](#Dive Into a World of Knowledge)
- [Storage](#storage)
  - [Schema](#schema)
- [Server](#server)
  - [Auth](#auth)
  - [API](#api)
- [Clients](#clients)
- [Hosting](#hosting)
- [Experience](#experience)
  - [Post List](#post-list)
  - [Tagging](#tagging)
  - [Comments](#comments)
  - [New Functionalities](#new-functionalities)
- [Market](#market)


## Dive Into a World of Knowledge

- Browse a curated feed: Explore a dynamic list of articles, videos, channels, and other web resources, constantly updated by fellow members.
- Find your niche with tags: Filter resources by language, topic, or skill level to tailor your learning experience.
- Up the ante with voting: Cast your vote on resources you find valuable, pushing the best content to the top and helping others navigate the information jungle.

## Engage with the Community

- Share your discoveries: Contribute valuable resources to the platform and share your insights with the community.
- Spark conversations: Leave comments and engage in discussions, enriching your understanding and building connections with fellow learners.
- Stay in the loop: A new notification system keeps you informed about relevant activity on your posts and comments, fostering a dynamic dialogue.


## Powerful Features, Seamless Experience:

- User profiles: Track your contributions, customize your profile, and build your reputation within the community.
- Search and sort: Find exactly what you're looking for with a powerful search function and various sorting options.
- React.js powered: Built with modern web technologies, Codersquare delivers a smooth and responsive user experience.


## More Than Just Learning:

Codersquare goes beyond a simple resource aggregator. It's a vibrant community of passionate developers where learning is collaborative, engaging, and always evolving.

Experience the future of learning. Come share, discover, and grow with Codersquare!

## Table of Contents

- [Storage](#storage)
  - [Schema](#schema)
- [Server](#server)
  - [Auth](#auth)
  - [API](#api)
- [Clients](#clients)
- [Hosting](#hosting)
- [Experience](#experience)
  - [Post List](#post-list)
  - [Tagging](#tagging)
  - [Comments](#comments)
  - [New Functionalities](#new-functionalities)
- [Market](#market)

## Storage

Codersquare utilizes a basic client/server architecture with a Spring Boot server deployed on a cloud provider alongside a relational database.

### Schema

#### Users:
| Column      | Type        |
|-------------|-------------|
| user_id     | BIGSERIAL    |
| first_name  | VARCHAR(255) |
| last_name   | VARCHAR(255) |
| username    | VARCHAR(255) |
| password    | VARCHAR(255) |
| email       | VARCHAR(255) |
| created_at  | TIMESTAMP    |
| UNIQUE (username), UNIQUE (email) |

#### Posts:
| Column      | Type        |
|-------------|-------------|
| post_id     | BIGSERIAL    |
| user_id     | BIGINT      |
| title       | VARCHAR(255) |
| category    | VARCHAR(50)  |
| url         | VARCHAR(255) |
| points      | INT         |
| created_at  | TIMESTAMP    |
| FOREIGN KEY (user_id) REFERENCES users (user_id) |

#### Likes:
| Column      | Type        |
|-------------|-------------|
| like_id     | BIGSERIAL    |
| user_id     | BIGINT      |
| post_id     | BIGINT      |
| created_at  | TIMESTAMP    |
| FOREIGN KEY (user_id) REFERENCES users (user_id) |
| FOREIGN KEY (post_id) REFERENCES posts (post_id) |

#### Comments:
| Column      | Type        |
|-------------|-------------|
| comment_id  | BIGSERIAL    |
| user_id     | BIGINT      |
| post_id     | BIGINT      |
| text        | TEXT        |
| points      | INT         |
| created_at  | TIMESTAMP    |
| FOREIGN KEY (user_id) REFERENCES users (user_id) |
| FOREIGN KEY (post_id) REFERENCES posts (post_id) |

#### Votes:
| Column      | Type        |
|-------------|-------------|
| vote_id     | BIGSERIAL   |
| user_id     | BIGINT      |
| post_id     | BIGINT      |
| comment_id  | BIGINT      |
| vote_type   | VARCHAR(50) |
| created_at  | TIMESTAMP   |
| FOREIGN KEY (user_id) REFERENCES users (user_id) |
| FOREIGN KEY (post_id) REFERENCES posts (post_id) |
| FOREIGN KEY (comment_id) REFERENCES comments (comment_id) |

#### Tokens:
| Column             | Type        |
|--------------------|-------------|
| token_id           | BIGSERIAL    |
| confirmation_token | VARCHAR(255) |
| created_at         | TIMESTAMP    |
| expired_at         | TIMESTAMP    |
| confirmed_at       | TIMESTAMP    |
| user_id            | BIGINT       |
| FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE |

## Server

A Spring Boot server is implemented to handle the backend functionalities. Authentication is based on JWT, with passwords encrypted and stored in the database. OAuth integration with platforms like Google, Facebook, and potentially Github is planned.

### API

**Auth:**
```
/signIn  [POST]
/signUp  [POST]
/signOut [POST]
```

**Posts:**
```
/posts/list [GET]
/posts/new  [POST]
/posts/:id  [GET]
/posts/:id  [DELETE]
/posts/sort/{criteria} [GET]    // Sort by Hot (Default), Recent or popularity (week, month, year, all time)
/posts/search/{query} [GET]    // Search resources based on a query

```

**Likes:**
```
/likes/new [POST] // like or unlike post using as bookmarks for users
```

**Comments:**
```
/comments/new  [POST]
/comments/list [GET]
/comments/:id  [DELETE]

```

**Users:**
```
/users/{username} [GET]        // Get user profile information
/users/{username}/posts [GET]        // Get all posts related to single user
/users/{username}/likes [GET]        // Get all likes related to single user

```
**Votes:**

```
/api/v1/vote [PUT] // Vote on a post or comment

/posts/upvote/{id} [POST]      // Upvote a post
/posts/downvote/{id} [POST]    // Downvote a post

/comments/upvote/{id} [POST]      // Upvote a comment
/comments/downvote/{id} [POST]    // Downvote a comment
```


## Clients

The web client, implemented in React.js, serves as the primary interface. It uses ReactQuery to communicate with the backend and Chakra UI for building CSS components.

## Hosting

The code is hosted on Github. //TODO: deplpy the project to AWS.

## Experience

### Post List

The landing page presents a list of links with titles given by posters. Links are scored based on popularity and age, allowing users to browse and navigate. Signed-in users can add comments, upvote posts or comment,like posts for bookmark, and manage their own posts.

### Tagging

Posts can have tags describing different attributes, allowing users to filter the experience using these tags, including language.

### Comments

Comments are sorted reverse-chronologically, and users can delete their own comments.

### Notification System

Codersquare now incorporates a notification system to enhance user engagement and interaction. This feature notifies users about relevant activities and updates within the platform. The following functionalities are included in the notification system:

1. **Likes and Bookmarks:**
   - Users receive notifications when their posts are liked by others or bookmarked by users.

2. **Comments:**
   - Notification alerts for new comments on the user's posts or replies to their comments.

The notification system aims to keep users informed about activities relevant to their contributions and interests on Codersquare, fostering a dynamic and engaging community experience.

If you have any specific requirements or additional features for the notification system, feel free to provide more details.


### New Functionalities

1. **Sorting:**
   - Users can filter resources based on criteria such as Sort by Recent or popularity (week, month, year, all time).

2. **Search:**
   - Implemented search functionality to search for resources based on a query.

3. **User Profiles:**
   - Users have profiles that can be customized. Retrieve user profile information via `/users/{username}`.

4. **Voting:**
   - Users can upvote or downvote posts and comments. The vote functionality is implemented using the `/api/v1/vote` endpoint. The details can be found in the request mapping for this endpoint.
  

5. **System Alerts:**
   - Important system-wide alerts and announcements are communicated through notifications.   

...



## Market

Codersquare offers a hybrid experience, combining elements of news sites like Hackernews or Reddit with learning sites that curate resources into a course-like experience, similar to Coursera, Udemy, or FreeCodeCamp.

---
