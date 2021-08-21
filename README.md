![logo](https://www.dariawan.com/media/images/tech-spring-boot.width-1024.png)

## 📚 BlogApplication
This is a blog Application in which user can create his own blog and that blog could be read by any user but only author can update abd delete the blog.

Designed the database schema and built the `API's using Spring Boot`.

## Subject
- User can be an editor and reader (to be a reader you don't have to sign in but to be an editor you have to create an account)
- Editor can create an article / update / delete his own articles (he should be signed in) (api for the same , authenticated)
- Editor should be able to see list of his articles on sign in . (api for the same)
- An article can have multiple tags like computer science, machine learning, garbage collection etc , editor can create a new tag or select existing tags, displayed as checklist (to simplify) (article creation api should be able to create articles and tags or assign existing tags)
- An api to get all articles belong to a tag (add tags / )
- Api to get all tags
- Any article or tag that has been created will have a slug and slug will be used in get apis.
- an api to get paginated articles ordered by publish date (can be used by readers (not logged in users))
- option to search articles using tags or editor or article name (if article subject/heading contain provided word) (combine this with above api)

## Tech Stack
- [Java](https://docs.oracle.com/javase/8/docs/)
- [MySQL](https://www.mysql.com/)
- [Spring Boot](https://spring.io/projects/spring-boot)

## HTTP Request Methods
- Get
- Put
- Post
- Delete

## HTTP responce code
#### 2XX Success      
| code | status |
|-|-|
| 200| OK |  
| 201 | Created | 
|202| Accepted|
| 201 | No Content|

#### 3XX Redirection
| code | status |
|-|-|
| 301| moved Permanently|
| 302 | Found|
| 304 | Not Modified |

#### 4XX Client Error
| code | status |
|-|-|
|400|Bad Request|
| 401| Unauthorized|
| 403 | Forbidden|
| 404 | Not Found |
|405| Method Not Allowed|
|408|Request Timeout|

#### 5XX Server Error
| code | status |
|-|-|
| 500| Internal Server error|
| 501 | Not Implemented|
| 502 | Bad Gateway|
|503| Service Unavailable|
|504|Gateway Timeout|

## Schema Design
![](https://raw.githubusercontent.com/kanojiaD/BlogApplication/master/Schema_Design.png?token=ATMHGIU7RDE74SSJNE6AFW3BECLYC)
## API Contract
|Functionality|Path |Method |Return Code|
|-|-|-|-|
|  |  |  |  |
