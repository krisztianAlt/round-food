# Round Food


**About the program**

Round Food is a demo food ordering website (https://round-food.herokuapp.com/).

As a user you can select food and read informations about it (description, price, optional extra toppings). Foods are classified into three categories: cake, pizza and pie.

If you register and log in, you can place the orders too: you can choose shipping date and time, payment method, then finalize your order. In addition you can modify your profile, and reorder any of your previous closed orders.

**About programming**

The back-end is written in Java, in Spring Boot framework. Thymeleaf is used to render front-end.

In this project I have wanted to practice:
- the implementation of a basic registration and login process with data validation;
- the implementation of shopping cart, which reacts to adding/deleting order items;
- the handling of shipping date and time: listening current date and time, and create the list of choosable shipping date and time by right of these.