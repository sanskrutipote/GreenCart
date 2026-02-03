-- Random Dataset for Agriculture Website
-- Run this after create_database_separate.sql to populate with sample data
-- Database: agriculture_website

USE agriculture_website;

-- =====================================================
-- CLEAR EXISTING DATA (Optional - uncomment if needed)
-- =====================================================
-- DELETE FROM order_items;
-- DELETE FROM orders;
-- DELETE FROM cropsales;
-- DELETE FROM posts;
-- DELETE FROM contactus;
-- DELETE FROM membership WHERE UserName != 'admin';

-- =====================================================
-- SAMPLE USERS (membership table)
-- =====================================================
INSERT INTO membership (UserName, Email, MobileNo, Password) VALUES
('farmer_raj', 'raj.kumar@gmail.com', '9876543210', 'password123'),
('krishna_farm', 'krishna.reddy@yahoo.com', '9845612378', 'farm2026'),
('suresh_agro', 'suresh.patel@hotmail.com', '9123456780', 'agro@123'),
('anita_crops', 'anita.sharma@gmail.com', '9988776655', 'crops456'),
('ravi_harvest', 'ravi.singh@outlook.com', '9654321098', 'harvest789'),
('priya_green', 'priya.nair@gmail.com', '9012345678', 'green2026'),
('mohan_fields', 'mohan.das@yahoo.com', '9876012345', 'fields123'),
('lakshmi_farm', 'lakshmi.iyer@gmail.com', '9543210987', 'lakshmi@farm'),
('vijay_agri', 'vijay.kumar@hotmail.com', '9321098765', 'vijay2026'),
('deepa_organic', 'deepa.menon@gmail.com', '9456789012', 'organic123'),
('ramesh_trader', 'ramesh.gupta@yahoo.com', '9234567890', 'trader456'),
('sunita_crops', 'sunita.verma@gmail.com', '9678901234', 'sunita789'),
('arun_harvest', 'arun.pillai@outlook.com', '9890123456', 'harvest2026'),
('kavitha_green', 'kavitha.rao@gmail.com', '9567890123', 'kavitha123'),
('sanjay_farm', 'sanjay.joshi@yahoo.com', '9345678901', 'sanjay@farm'),
('meera_agro', 'meera.nair@hotmail.com', '9789012345', 'meera2026'),
('gopal_fields', 'gopal.sharma@gmail.com', '9456012378', 'gopal123'),
('geeta_organic', 'geeta.patel@yahoo.com', '9123098765', 'geeta456'),
('ashok_trader', 'ashok.reddy@gmail.com', '9012876543', 'ashok789'),
('padma_crops', 'padma.das@outlook.com', '9987654321', 'padma2026')
ON DUPLICATE KEY UPDATE Email = VALUES(Email);

-- =====================================================
-- CONTACT FORM SUBMISSIONS (contactus table)
-- =====================================================
INSERT INTO contactus (Name, Email, MobileNo, Type, Message) VALUES
('Rajesh Kumar', 'rajesh.k@gmail.com', '9876543210', 'General', 'I would like to know more about the crop pricing in your marketplace. Can you provide details about how prices are determined?'),
('Ananya Sharma', 'ananya.s@yahoo.com', '9845612378', 'Support', 'I am facing issues with my order #1234. The delivery has been delayed by 5 days. Please help resolve this issue.'),
('Vikram Singh', 'vikram.singh@hotmail.com', '9123456780', 'Partnership', 'Our company is interested in partnering with GreenCart for bulk vegetable purchases. Please contact us to discuss terms.'),
('Priya Nair', 'priya.n@gmail.com', '9988776655', 'Feedback', 'Excellent platform! I have been using GreenCart for 6 months and the quality of produce is always top-notch. Keep up the good work!'),
('Suresh Patel', 'suresh.p@outlook.com', '9654321098', 'General', 'What are the requirements to become a seller on this platform? I have a farm and want to sell my crops here.'),
('Kavitha Rao', 'kavitha.rao@gmail.com', '9012345678', 'Support', 'My account login is not working since yesterday. I have tried resetting password but still facing issues.'),
('Mohan Das', 'mohan.das@yahoo.com', '9876012345', 'Partnership', 'We are a restaurant chain looking to source fresh vegetables directly from farmers. Interested in long-term partnership.'),
('Lakshmi Iyer', 'lakshmi.i@gmail.com', '9543210987', 'Feedback', 'The machine rental service is very helpful for small farmers like me. Suggestion: Please add more tractors in South region.'),
('Vijay Kumar', 'vijay.k@hotmail.com', '9321098765', 'General', 'How can I track my order? I placed an order 3 days ago but cannot find tracking information anywhere.'),
('Deepa Menon', 'deepa.m@gmail.com', '9456789012', 'Support', 'Payment failed but amount was deducted from my account. Transaction ID: TXN987654321. Please refund immediately.');

-- =====================================================
-- MACHINE/PRODUCT POSTS (posts table)
-- =====================================================
INSERT INTO posts (Name, Email, Mobile, Price, location, image, Owner) VALUES
('John Deere Tractor 5050D', 'farmer_raj@gmail.com', '9876543210', 850000.00, 'Bangalore, Karnataka', 'Images/tractor1.jpg', 'farmer_raj'),
('Mahindra 575 DI Tractor', 'krishna.reddy@yahoo.com', '9845612378', 720000.00, 'Hyderabad, Telangana', 'Images/tractor2.jpg', 'krishna_farm'),
('Rotavator 6ft Heavy Duty', 'suresh.patel@hotmail.com', '9123456780', 125000.00, 'Ahmedabad, Gujarat', 'Images/rotavator.jpg', 'suresh_agro'),
('Combine Harvester Mini', 'anita.sharma@gmail.com', '9988776655', 1500000.00, 'Ludhiana, Punjab', 'Images/harvester.jpg', 'anita_crops'),
('Seed Drill Machine', 'ravi.singh@outlook.com', '9654321098', 85000.00, 'Jaipur, Rajasthan', 'Images/seeddrill.jpg', 'ravi_harvest'),
('Power Sprayer 20L', 'priya.nair@gmail.com', '9012345678', 15000.00, 'Coimbatore, Tamil Nadu', 'Images/sprayer.jpg', 'priya_green'),
('Water Pump 5HP', 'mohan.das@yahoo.com', '9876012345', 28000.00, 'Nagpur, Maharashtra', 'Images/waterpump.jpg', 'mohan_fields'),
('Paddy Transplanter', 'lakshmi.i@gmail.com', '9543210987', 320000.00, 'Thanjavur, Tamil Nadu', 'Images/transplanter.jpg', 'lakshmi_farm'),
('Cultivator 9 Tyne', 'vijay.k@hotmail.com', '9321098765', 45000.00, 'Indore, Madhya Pradesh', 'Images/cultivator.jpg', 'vijay_agri'),
('Thresher Multi Crop', 'deepa.m@gmail.com', '9456789012', 175000.00, 'Patna, Bihar', 'Images/thresher.jpg', 'deepa_organic'),
('Chaff Cutter Electric', 'ramesh.gupta@yahoo.com', '9234567890', 32000.00, 'Lucknow, Uttar Pradesh', 'Images/chaffcutter.jpg', 'ramesh_trader'),
('Drip Irrigation Kit', 'sunita.verma@gmail.com', '9678901234', 18000.00, 'Nashik, Maharashtra', 'Images/drip.jpg', 'sunita_crops'),
('Greenhouse Setup 1000sqft', 'arun.pillai@outlook.com', '9890123456', 250000.00, 'Pune, Maharashtra', 'Images/greenhouse.jpg', 'arun_harvest'),
('Manual Rice Mill', 'kavitha.rao@gmail.com', '9567890123', 55000.00, 'Mysore, Karnataka', 'Images/ricemill.jpg', 'kavitha_green'),
('Solar Water Pump System', 'sanjay.joshi@yahoo.com', '9345678901', 180000.00, 'Rajkot, Gujarat', 'Images/solarpump.jpg', 'sanjay_farm');

-- =====================================================
-- CROP SALES (cropsales table)
-- =====================================================
INSERT INTO cropsales (cropid, Quantity, Name, address, mobileNo) VALUES
('RICE001', '500 kg', 'Ramesh Gupta', '45 Main Road, Lucknow, UP 226001', '9234567890'),
('WHEAT002', '1000 kg', 'Sunita Verma', '78 Farm Colony, Indore, MP 452001', '9678901234'),
('POTATO003', '300 kg', 'Arun Pillai', '23 Green Valley, Pune, MH 411001', '9890123456'),
('CORN004', '750 kg', 'Kavitha Rao', '56 Agri Lane, Mysore, KA 570001', '9567890123'),
('RICE005', '2000 kg', 'Sanjay Joshi', '89 Field View, Rajkot, GJ 360001', '9345678901'),
('TOMATO006', '400 kg', 'Meera Nair', '12 Market Road, Kochi, KL 682001', '9789012345'),
('ONION007', '600 kg', 'Gopal Sharma', '34 Village Center, Nashik, MH 422001', '9456012378'),
('CABBAGE008', '250 kg', 'Geeta Patel', '67 Farm House, Surat, GJ 395001', '9123098765'),
('WHEAT009', '1500 kg', 'Ashok Reddy', '90 Crop Street, Warangal, TS 506001', '9012876543'),
('RICE010', '800 kg', 'Padma Das', '43 Harvest Road, Cuttack, OD 753001', '9987654321');

-- =====================================================
-- ORDERS (orders table)
-- =====================================================
INSERT INTO orders (UserName, TotalAmount, Address, Status) VALUES
('farmer_raj', 2500.00, '123 Farm Road, Bangalore, Karnataka 560001', 'Delivered'),
('krishna_farm', 4200.00, '456 Agri Street, Hyderabad, Telangana 500001', 'Shipped'),
('suresh_agro', 1800.00, '789 Green Lane, Ahmedabad, Gujarat 380001', 'Processing'),
('anita_crops', 3500.00, '321 Crop Circle, Ludhiana, Punjab 141001', 'Pending'),
('ravi_harvest', 5600.00, '654 Harvest View, Jaipur, Rajasthan 302001', 'Delivered');

-- =====================================================
-- ORDER ITEMS (order_items table)
-- =====================================================
-- Order 1 Items (farmer_raj - Total: 2500)
INSERT INTO order_items (OrderId, ItemName, Quantity, Price) VALUES
(1, 'Sweet Potato', 5, 200.00),
(1, 'Corn', 3, 180.00),
(1, 'Rice', 8, 120.00);

-- Order 2 Items (krishna_farm - Total: 4200)
INSERT INTO order_items (OrderId, ItemName, Quantity, Price) VALUES
(2, 'Wheat', 10, 160.00),
(2, 'Potatoes', 15, 90.00),
(2, 'Vegetables', 8, 110.00);

-- Order 3 Items (suresh_agro - Total: 1800)
INSERT INTO order_items (OrderId, ItemName, Quantity, Price) VALUES
(3, 'Pomegranate', 4, 210.00),
(3, 'Leeks', 6, 70.00),
(3, 'Radish', 10, 60.00);

-- Order 4 Items (anita_crops - Total: 3500)
INSERT INTO order_items (OrderId, ItemName, Quantity, Price) VALUES
(4, 'Cauliflower', 8, 130.00),
(4, 'Soya Beans', 10, 170.00),
(4, 'Legumes', 5, 140.00);

-- Order 5 Items (ravi_harvest - Total: 5600)
INSERT INTO order_items (OrderId, ItemName, Quantity, Price) VALUES
(5, 'Rice', 20, 120.00),
(5, 'Wheat', 15, 160.00),
(5, 'Sweet Potato', 5, 200.00);

-- =====================================================
-- VERIFY DATA
-- =====================================================
SELECT 'Data inserted successfully!' AS Status;
SELECT 'membership' AS TableName, COUNT(*) AS RecordCount FROM membership
UNION ALL
SELECT 'contactus', COUNT(*) FROM contactus
UNION ALL
SELECT 'posts', COUNT(*) FROM posts
UNION ALL
SELECT 'cropsales', COUNT(*) FROM cropsales
UNION ALL
SELECT 'orders', COUNT(*) FROM orders
UNION ALL
SELECT 'order_items', COUNT(*) FROM order_items;
