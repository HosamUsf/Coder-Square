---

# Codersquare

Codersquare is a social web app designed for sharing learning resources in a hackernews-style experience. It allows users to post links to articles, videos, channels, or other public resources on the web, and enables other users to vote and comment on those resources.

![Screenshot](/../main/docs/ui%20designs/1%20home%20page.png)

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
/posts/sort/{criteria} [GET]    // Sort by Recent or popularity (week, month, year, all time)
/posts/search/{query} [GET]    // Search resources based on a query
/posts/upvote/{id} [POST]      // Upvote a post
/posts/downvote/{id} [POST]    // Downvote a post
```

**Likes:**
```
/likes/new [POST]
/likes/upvote/{postId} [POST]    // Upvote a post
/likes/downvote/{postId} [POST]  // Downvote a post
```

**Comments:**
```
/comments/new  [POST]
/comments/list [GET]
/comments/:id  [DELETE]
/comments/upvote/{id} [POST]      // Upvote a comment
/comments/downvote/{id} [POST]    // Downvote a comment
```

**Users:**
```
/users/{username} [GET]        // Get user profile information
```

## Clients

The web client, implemented in React.js, serves as the primary interface. It uses ReactQuery to communicate with the backend and Chakra UI for building CSS components.

## Hosting

The code is hosted on Github. The web client is hosted on platforms like Firebase or Netlify, and the Spring Boot server is deployed to a (likely shared) VPS.

## Experience

### Post List

The landing page presents a list of links with titles given by posters. Links are scored based on popularity and age, allowing users to browse and navigate. Signed-in users can add comments, upvote posts, and manage their own posts.

### Tagging

Posts can have tags describing different attributes, allowing users to filter the experience using these tags, including language.

### Comments

Comments are sorted reverse-chronologically, and users can delete their own comments.

### New Functionalities

1. **Sorting:**
   - Users can filter resources based on criteria such as Sort by Recent or popularity (week, month, year, all time).

2. **Search:**
   - Implemented search functionality to search for resources based on a query.

3. **User Profiles:**
   - Users have profiles that can be customized. Retrieve user profile information via `/users/{username}`.

4. **Voting:**
   - Users can upvote or downvote posts and comments.

## Market

Codersquare offers a hybrid experience, combining elements of news sites like Hackernews or Reddit with learning sites that curate resources into a course-like experience, similar to Coursera, Udemy, or FreeCodeCamp.

---
