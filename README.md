<h1 align="center">Time-For-Meal-Social-Media</h1>

![all-products](https://i.imgur.com/BAKDw0o.png)

<br>

## Content
  1. [Info](#Info)
  2. [Technologies](#Technologies)
  3. [User guide](#Guide)
  4. [Functionality](#Functionality)
  5. [Database](#Database)
  
  
  
<br>
<br>

## <a name="Info"></a> Project Info

Time for meal is a social media app for food lovers built with Spring boot, React, and MySQL. 
You can share your recipes, rate, like, comment on other people's recipes, and much more. 
It has over 2200+ ingredients in the database. It has full user authentication using JWT tokens with email verification and reset password functionality.
Data is validated both on the front-end and back-end side.

<br>
<br>

## <a name="Technologies"></a> Technologies
   
<h3>Back-end</h3>
   <ul>
       <li>Java</li>
       <li>Spring boot</li>
       <li>Spring Security</li>
       <li>JWT</li>
       <li>Spring Data JPA</li>
       <li>MySQL</li>
       <li>JUnit & Mockito</li>
       <li>Spring mail</li>
   </ul>

<br>

<h3>Front-end</h3>
   <ul>
     <li>Javascript</li>
     <li>React</li>
     <li>React Router</li>
     <li>SweetAlert2 (for alerts)</li>
     <li>SCSS</li>
   </ul>  


<br>
<br>

## <a name="Guide"></a> User guide

<h3>Back-end setup:</h3>

  1. You need to execute a script for creating the database that is in the file `mysql/timeformeal_db.sql`.
  2. Modify the configuration to suit your settings. All settings can be found in the file `src/main/resources/application.properties`. 
      1. Change `spring.datasource.username` and `spring.datasource.password`
      2. Change `spring.mail.username` and `spring.mail.password`
      3. Change `jwt.secret`
      4. Change `frontend.url` to match your react app
  3. Finally, you can start server by running `main()` method inside `src/main/java/com/veljkocerovic/timeformeal/TimeForMealApplication.java` file.


<br>

<h3>Front-end setup:</h3>

1. First go to `src/frontend` and in terminal run `npm install` to install all dependencies.
2. Now create `.env` file and add `REACT_APP_API_URL=backend_url_here` replace property to match your backend url.
3. Finally, you can start server by running `npm start`


<br>
<br>


## <a name="Functionality"></a> Functionality

<br>

>**1.** View all recipes.

<br>

![all-recipes](https://i.imgur.com/BAKDw0o.png)

<br>

>**2.** Search recipes.

<br>

![search-recipe](https://i.imgur.com/7XuVIgK.png)

<br>

>**3.** Manage your profile, recipes, info.

<br>

![manage-profile](https://i.imgur.com/ArufdmD.png)


<br>

>**4.** View recipe, rate, like, comment.

<br>


![view-recipe](https://i.imgur.com/32GQ6zY.png)

<br>

>**5.** Post new recipe.

<br>


![post-new-recipe](https://i.imgur.com/LFDJ1Xp.png)

<br>

>**6.** Register.

<br>


![register](https://i.imgur.com/VSIofHM.png)

<br>

>**7.** Login.

<br>


![login](https://i.imgur.com/TQTVgbU.png)

<br>

>**7.** Edit username, email, image.

<br>


![edit-user](https://i.imgur.com/RxpWH5X.png)


<br>

>**7.** When you register, you will recive email verification link.

<br>


![verification](https://i.imgur.com/Ioeggu0.png)

<br>
<br>


## <a name="Database"></a> Database Diagram

![Database Diagram](https://imgur.com/m8zUCxL.png)


