/*insert veg pizza*/
insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('1','Deluxe Veggie','Vegetarian','Regular','150.00','5');
insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('2','Deluxe Veggie','Vegetarian','Medium','200.00','5');
insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('3','Deluxe Veggie','Vegetarian','Large','325.00','5');

insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('4','Cheese and corn','Vegetarian','Regular','175.00','5');
insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('5','Cheese and corn','Vegetarian','Medium','375.00','5');
insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('6','Cheese and corn','Vegetarian','Large','475.00','5');

insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('7','Paneer Tikka','Vegetarian','Regular','160.00','5');
insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('8','Paneer Tikka','Vegetarian','Medium','290.00','5');
insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('9','Paneer Tikka','Vegetarian','Large','340.00','5');

/*insert non veg pizza*/
insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('10','Non-­Veg Supreme','Non-­Vegetarian','Regular','190.00','5');
insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('11','Non-­Veg Supreme','Non-­Vegetarian','Medium','325.00','5');
insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('12','Non-­Veg Supreme','Non-­Vegetarian','Large','425.00','5');

insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('13','Chicken Tikka','Non-­Vegetarian','Regular','210.00','5');
insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('14','Chicken Tikka','Non-­Vegetarian','Medium','370.00','5');
insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('15','Chicken Tikka','Non-­Vegetarian','Large','500.00','5');

insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('16','Pepper Barbecue Chicken','Non-­Vegetarian','Regular','210.00','5');
insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('17','Pepper Barbecue Chicken','Non-­Vegetarian','Medium','370.00','5');
insert into pizza_info(`pizza_info_id`,`pizza_name`,`pizza_category`,`pizza_size`,`price`,`stock_quantity`) values ('18','Pepper Barbecue Chicken','Non-­Vegetarian','Large','500.00','5');

/*insert crust  */
insert into additional_stuff(`stuff_name`,`stuff_category`,`price`,`stock_quantity`) values ('New hand tossed','crust','0.0','10');
insert into additional_stuff(`stuff_name`,`stuff_category`,`price`,`stock_quantity`) values ('Wheat thin crust','crust','0.0','10');
insert into additional_stuff(`stuff_name`,`stuff_category`,`price`,`stock_quantity`) values ('Cheese Burst','crust','0.0','10');
insert into additional_stuff(`stuff_name`,`stuff_category`,`price`,`stock_quantity`) values ('Fresh pan pizza','crust','0.0','10');

/*insert veg toppings*/
insert into additional_stuff(`stuff_name`,`stuff_category`,`price`,`stock_quantity`) values ('Black olive','Veg toppings','20.0','10');
insert into additional_stuff(`stuff_name`,`stuff_category`,`price`,`stock_quantity`) values ('Capsicum ','Veg toppings','25.0','10');
insert into additional_stuff(`stuff_name`,`stuff_category`,`price`,`stock_quantity`) values ('Paneer','Veg toppings','35.0','10');
insert into additional_stuff(`stuff_name`,`stuff_category`,`price`,`stock_quantity`) values ('Mushroom','Veg toppings','30.0','10');
insert into additional_stuff(`stuff_name`,`stuff_category`,`price`,`stock_quantity`) values ('Fresh tomato','Veg toppings','10.0','10');

/*insert non veg toppings*/
insert into additional_stuff(`stuff_name`,`stuff_category`,`price`,`stock_quantity`) values ('Chicken tikka','Non-­Veg toppings','35.0','10');
insert into additional_stuff(`stuff_name`,`stuff_category`,`price`,`stock_quantity`) values ('Barbeque chicken','Non-­Veg toppings','45.0','10');
insert into additional_stuff(`stuff_name`,`stuff_category`,`price`,`stock_quantity`) values ('Grilled chicken','Non-­Veg toppings','40.0','10');

/*insert extra cheese*/
insert into additional_stuff(`stuff_name`,`stuff_category`,`price`,`stock_quantity`) values ('Extra cheese','miscellaneous','40.0','10');

/*insert sides*/
insert into additional_stuff(`stuff_name`,`stuff_category`,`price`,`stock_quantity`) values ('Cold drink','sides','55.0','10');
insert into additional_stuff(`stuff_name`,`stuff_category`,`price`,`stock_quantity`) values ('Mousse cake','sides','90.0','10');
