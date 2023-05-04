-- SQLite
SELECT * from Item;
SELECT * from Orders;
SELECT * from persons;
SELECT * from OrderContains; 






























-- ** create item table

-- drop TABLE Item;
-- CREATE TABLE Item(ItemID INTEGER PRIMARY KEY AUTOINCREMENT
-- , Name VARCHAR(100)
-- , Brand VARCHAR(100)
-- , Amount FLOAT
-- , Price FLOAT
-- , Category VARCHAR(100)
-- );

-- DELETE from Item;

-- **this data for items 
-- INSERT into Item(Name, Brand, Amount, Price, Category)
-- VALUES
-- ('Milk Toffee', 'Toffee Co.', 100, 0.50, 'Classic'),
-- ('Dark Toffee', 'Toffee Co.', 50, 0.75,  'Classic'),
-- ('Caramel Toffee', 'Toffee Co.', 75, 1.00, 'Classic'),
-- ('Butterscotch Toffee', 'Toffee Co.', 60, 0.75,  'Classic'),
-- ('Coconut Toffee', 'Toffee Co.', 40, 1.25,'Exotic'),
-- ('Coffee Toffee', 'Toffee Co.', 30, 1.50,  'Exotic'),
-- ('Fruit Toffee', 'Toffee Co.', 25, 1.00,  'Fruit'),
-- ('Nougat Toffee', 'Toffee Co.', 20, 2.00,  'Nuts'),
-- ('Peanut Toffee', 'Toffee Co.', 35, 1.25,  'Nuts'),
-- ('Mint Toffee', 'Toffee Co.', 45, 1.50,  'Flavored'),
-- ('Strawberry Toffee', 'Toffee Co.', 15, 1.00,  'Fruit'),
-- ('Orange Toffee', 'Toffee Co.', 20, 0.75,  'Fruit'),
-- ('Lemon Toffee', 'Toffee Co.', 30, 1.25,  'Fruit'),
-- ('Licorice Toffee', 'Toffee Co.', 10, 1.50,  'Flavored'),
-- ('Ginger Toffee', 'Toffee Co.', 25, 1.00,  'Spiced'),
-- ('Cardamom Toffee', 'Toffee Co.', 18, 1.50,  'Spiced'),
-- ('Cinnamon Toffee', 'Toffee Co.', 22, 1.25,  'Spiced'),
-- ('Peppermint Toffee', 'Toffee Co.', 28, 1.50,  'Flavored'),
-- ('Raspberry Toffee', 'Toffee Co.', 17, 1.00,  'Fruit'),
-- ('Blueberry Toffee', 'Toffee Co.', 14, 1.00,  'Fruit'),
-- ('Cherry Toffee', 'Toffee Co.', 12, 1.00,  'Fruit'),
-- ('Watermelon Toffee', 'Toffee Co.', 11, 1.00,  'Fruit'),
-- ('Passion Fruit Toffee', 'Toffee Co.', 8, 1.25,  'Exotic'),
-- ('Mango Toffee', 'Toffee Co.', 6, 1.25,  'Exotic'),
-- ('Pineapple Toffee', 'Toffee Co.', 7, 1.25,  'Exotic'),
-- ('Pomegranate Toffee', 'Toffee Co.', 9, 1.25,  'Exotic'),
-- ('Blackcurrant Toffee', 'Toffee Co.', 5, 1.50,  'Fruit'),
-- ('Apple Toffee', 'Toffee Co.', 10, 0.75,  'Fruit'),
-- ('Banana Toffee', 'Toffee Co.', 13, 0.75,  'Fruit'),
-- ('Grape Toffee', 'Toffee Co.', 16, 0.75,  'Fruit'),
-- ('Pear Toffee', 'Toffee Co.', 18, 0.75,  'Fruit'),
-- ('Hazelnut Toffee', 'Toffee Co.', 20, 1.50,  'Nuts'),
-- ('Almond Toffee', 'Toffee Co.', 15, 1.50,  'Nuts'),
-- ('Pistachio Toffee', 'Toffee Co.', 12, 1.75,  'Nuts'),
-- ('Cashew Toffee', 'Toffee Co.', 8, 1.75,  'Nuts'),
-- ('Macadamia Toffee', 'Toffee Co.', 5, 2.00,  'Nuts'),
-- ('White Chocolate Toffee', 'Toffee Co.', 30, 1.75,  'Chocolate'),
-- ('Dark Chocolate Toffee', 'Toffee Co.', 25, 2.00,  'Chocolate'),
-- ('Milk Chocolate Toffee', 'Toffee Co.', 35, 1.50,  'Chocolate'),
-- ('Caramel Chocolate Toffee', 'Toffee Co.', 20, 2.50,  'Chocolate'),
-- ('Salted Caramel Toffee', 'Toffee Co.', 15, 1.75,  'Caramel'),
-- ('Chocolate Caramel Toffee', 'Toffee Co.', 10, 2.00,  'Caramel'),
-- ('Vanilla Toffee', 'Toffee Co.', 12, 1.50,  'Vanilla'),
-- ('Chocolate Vanilla Toffee', 'Toffee Co.', 7, 2.00,  'Vanilla'),
-- ('Coffee Vanilla Toffee', 'Toffee Co.', 5, 2.25,  'Vanilla'),
-- ('Chocolate Hazelnut Toffee', 'Toffee Co.', 18, 1.75,  'Nuts'),
-- ('Chocolate Almond Toffee', 'Toffee Co.', 14, 1.75,  'Nuts'),
-- ('Chocolate Pistachio Toffee', 'Toffee Co.', 10, 2.00,  'Nuts'),
-- ('Chocolate Cashew Toffee', 'Toffee Co.', 7, 2.00,  'Nuts'),
-- ('Chocolate Macadamia Toffee', 'Toffee Co.', 4, 2.25,  'Nuts'),
-- ('Maple Toffee', 'Toffee Co.', 12, 1.50,  'Caramel')
