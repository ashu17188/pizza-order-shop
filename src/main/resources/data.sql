/*insert veg pizza*/
insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('1001','Deluxe Veggie','Vegetarian','Regular','150.00','5');
insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('1002','Deluxe Veggie','Vegetarian','Medium','200.00','5');
insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('1003','Deluxe Veggie','Vegetarian','Large','325.00','5');

insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('1004','Cheese and corn','Vegetarian','Regular','175.00','5');
insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('1005','Cheese and corn','Vegetarian','Medium','375.00','5');
insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('1006','Cheese and corn','Vegetarian','Large','475.00','5');

insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('1007','Paneer Tikka','Vegetarian','Regular','160.00','5');
insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('1008','Paneer Tikka','Vegetarian','Medium','290.00','5');
insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('1009','Paneer Tikka','Vegetarian','Large','340.00','5');

/*insert non veg pizza*/
insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('1010','Non-­Veg Supreme','Non-­Vegetarian','Regular','190.00','5');
insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('1011','Non-­Veg Supreme','Non-­Vegetarian','Medium','325.00','5');
insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('1012','Non-­Veg Supreme','Non-­Vegetarian','Large','425.00','5');

insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('1013','Chicken Tikka','Non-­Vegetarian','Regular','210.00','5');
insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('1014','Chicken Tikka','Non-­Vegetarian','Medium','370.00','5');
insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('1015','Chicken Tikka','Non-­Vegetarian','Large','500.00','5');

insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('1016','Pepper Barbecue Chicken','Non-­Vegetarian','Regular','210.00','5');
insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('1017','Pepper Barbecue Chicken','Non-­Vegetarian','Medium','370.00','5');
insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('1018','Pepper Barbecue Chicken','Non-­Vegetarian','Large','500.00','5');

/*insert crust  */
insert into additional_stuff(`stuff_name`,`stuff_category`,`price`,`stock_quantity`) values ('New hand tossed','crust','0.0','10');
insert into additional_stuff(`stuff_name`,`stuff_category`,`price`,`stock_quantity`) values ('Wheat thin crust','crust','0.0','10');
insert into additional_stuff(`stuff_name`,`stuff_category`,`price`,`stock_quantity`) values ('Cheese Burst','crust','0.0','10');
insert into additional_stuff(`stuff_name`,`stuff_category`,`price`,`stock_quantity`) values ('Fresh pan pizza','crust','0.0','10');

/*insert veg toppings*/
insert into additional_stuff(`stuff_name`,`stuff_category`,`price`,`stock_quantity`) values ('Black olive','Veg Toppings','20.0','40');
insert into additional_stuff(`stuff_name`,`stuff_category`,`price`,`stock_quantity`) values ('Capsicum ','Veg Toppings','25.0','10');
insert into additional_stuff(`stuff_name`,`stuff_category`,`price`,`stock_quantity`) values ('Paneer','Veg Toppings','35.0','20');
insert into additional_stuff(`stuff_name`,`stuff_category`,`price`,`stock_quantity`) values ('Mushroom','Veg Toppings','30.0','10');
insert into additional_stuff(`stuff_name`,`stuff_category`,`price`,`stock_quantity`) values ('Fresh tomato','Veg Toppings','10.0','10');

/*insert non Veg Toppings*/
insert into additional_stuff(`stuff_name`,`stuff_category`,`price`,`stock_quantity`) values ('Chicken tikka','Non-­Veg Toppings','35.0','10');
insert into additional_stuff(`stuff_name`,`stuff_category`,`price`,`stock_quantity`) values ('Barbeque chicken','Non-­Veg Toppings','45.0','10');
insert into additional_stuff(`stuff_name`,`stuff_category`,`price`,`stock_quantity`) values ('Grilled chicken','Non-­Veg Toppings','40.0','20');

/*insert extra cheese*/
insert into additional_stuff(`stuff_name`,`stuff_category`,`price`,`stock_quantity`) values ('Extra cheese','miscellaneous','35.0','30');

/*insert sides*/
insert into additional_stuff(`stuff_name`,`stuff_category`,`price`,`stock_quantity`) values ('Cold drink','sides','55.0','50');
insert into additional_stuff(`stuff_name`,`stuff_category`,`price`,`stock_quantity`) values ('Mousse cake','sides','90.0','50');