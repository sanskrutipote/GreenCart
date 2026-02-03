-- Insert Sample Data into agriculture_website database
USE agriculture_website;

-- Sample Users/Farmers
INSERT INTO membership (UserName, email, mobileNo, Password, userType, location, profileImage) VALUES
('farmer_harsha', 'harsha@agzone.com', '9876543210', 'pass123', 'farmer', 'Karnataka', 'farmer1.jpg'),
('farmer_akshay', 'akshay@agzone.com', '9876543211', 'pass123', 'farmer', 'Maharashtra', 'farmer2.jpg'),
('farmer_rushabh', 'rushabh@agzone.com', '9876543212', 'pass123', 'farmer', 'Rajasthan', 'farmer3.jpg'),
('farmer_srivatsan', 'srivatsan@agzone.com', '9876543213', 'pass123', 'farmer', 'Tamil Nadu', 'farmer4.jpg'),
('farmer_anurag', 'anurag@agzone.com', '9876543214', 'pass123', 'farmer', 'Uttar Pradesh', 'farmer5.jpg'),
('buyer_john', 'john@buyer.com', '9876543215', 'pass123', 'buyer', 'Delhi', 'buyer1.jpg'),
('buyer_sarah', 'sarah@buyer.com', '9876543216', 'pass123', 'buyer', 'Mumbai', 'buyer2.jpg'),
('buyer_amit', 'amit@buyer.com', '9876543217', 'pass123', 'buyer', 'Bangalore', 'buyer3.jpg'),
('buyer_priya', 'priya@buyer.com', '9876543218', 'pass123', 'buyer', 'Hyderabad', 'buyer4.jpg'),
('admin_user', 'admin@agzone.com', '9999999999', 'admin123', 'admin', 'India', 'admin.jpg');

-- Sample Crops (For Buy/Sell)
INSERT INTO crops (cropName, category, price, quantity, unit, description, image, seller_id, location, harvestDate, available) VALUES
('Premium Wheat', 'Cereals', 2500.00, 1000, 'kg', 'High-quality wheat from Punjab', 'wheat.jpg', 1, 'Punjab', '2025-03-15', TRUE),
('Basmati Rice', 'Cereals', 5000.00, 500, 'kg', 'Premium basmati rice from India', 'rice.jpg', 2, 'Haryana', '2025-02-20', TRUE),
('Fresh Tomatoes', 'Vegetables', 40.00, 5000, 'kg', 'Organic tomatoes, freshly harvested', 'tomatoes.jpg', 3, 'Maharashtra', '2025-01-28', TRUE),
('Sweet Potatoes', 'Vegetables', 35.00, 3000, 'kg', 'Nutritious sweet potatoes', 'sweetpotato.jpg', 4, 'West Bengal', '2025-01-25', TRUE),
('Onions', 'Vegetables', 30.00, 8000, 'kg', 'Fresh golden onions', 'onions.jpg', 5, 'Rajasthan', '2025-01-20', TRUE),
('Carrots', 'Vegetables', 45.00, 2000, 'kg', 'Orange and crunchy carrots', 'carrots.jpg', 1, 'Punjab', '2025-01-28', TRUE),
('Green Beans', 'Vegetables', 60.00, 1500, 'kg', 'Fresh green beans', 'greenbeans.jpg', 2, 'Himachal Pradesh', '2025-01-27', TRUE),
('Mango (Alphonso)', 'Fruits', 120.00, 2000, 'kg', 'Sweet Alphonso mangoes - King of fruits', 'mango.jpg', 3, 'Maharashtra', '2025-05-15', FALSE),
('Apple (Kashmir)', 'Fruits', 80.00, 1500, 'kg', 'Fresh Kashmir apples', 'apples.jpg', 4, 'Jammu & Kashmir', '2025-02-01', TRUE),
('Banana', 'Fruits', 25.00, 5000, 'kg', 'Yellow ripened bananas', 'banana.jpg', 5, 'Kerala', '2025-01-28', TRUE),
('Grapes', 'Fruits', 100.00, 1000, 'kg', 'Sweet green grapes', 'grapes.jpg', 1, 'Maharashtra', '2025-02-10', TRUE),
('Corn (Maize)', 'Cereals', 3000.00, 2000, 'kg', 'Yellow corn for commercial use', 'corn.jpg', 2, 'Haryana', '2025-01-15', TRUE),
('Mustard Seeds', 'Oil Seeds', 5500.00, 800, 'kg', 'High oil content mustard seeds', 'mustard.jpg', 3, 'Rajasthan', '2025-01-10', TRUE),
('Groundnut', 'Oil Seeds', 6500.00, 600, 'kg', 'Quality groundnut kernels', 'groundnut.jpg', 4, 'Gujarat', '2025-01-05', TRUE),
('Cotton', 'Cash Crops', 8000.00, 500, 'kg', 'Pure white cotton', 'cotton.jpg', 5, 'Telangana', '2025-02-28', FALSE);

-- Sample Machinery Posts (For Rent/Sell)
INSERT INTO posts (name, email, mobile, price, location, image, owner, postType, description, specifications, condition, userId) VALUES
('John Deere Tractor', 'harsha@agzone.com', '9876543210', 850000.00, 'Karnataka', 'tractor1.jpg', 'farmer_harsha', 'sell', 'Powerful tractor for all farm operations', '45 HP, 2020 Model', 'excellent', 1),
('Bajaj Tractor', 'akshay@agzone.com', '9876543211', 650000.00, 'Maharashtra', 'tractor2.jpg', 'farmer_akshay', 'sell', 'Budget-friendly tractor', '40 HP, 2019 Model', 'good', 2),
('Combine Harvester', 'rushabh@agzone.com', '9876543212', 1200000.00, 'Rajasthan', 'combine.jpg', 'farmer_rushabh', 'sell', 'Modern combine harvester for wheat and rice', '12 feet cutting width', 'excellent', 3),
('Power Tiller', 'srivatsan@agzone.com', '9876543213', 150000.00, 'Tamil Nadu', 'tiller.jpg', 'farmer_srivatsan', 'sell', 'Compact power tiller for small farms', '7 HP, Diesel', 'good', 4),
('Rotavator', 'anurag@agzone.com', '9876543214', 80000.00, 'Uttar Pradesh', 'rotavator.jpg', 'farmer_anurag', 'sell', 'Soil preparation equipment', '6 feet width', 'average', 5),
('Tractor Rent - 40 HP', 'harsha@agzone.com', '9876543210', 1500.00, 'Karnataka', 'tractor_rent.jpg', 'farmer_harsha', 'rent', 'Rent high-quality tractor for seasonal work', '40 HP, Fuel included', 'excellent', 1),
('Combine Harvester Rent', 'akshay@agzone.com', '9876543211', 3000.00, 'Maharashtra', 'combine_rent.jpg', 'farmer_akshay', 'rent', 'Professional combine harvester rental', 'Per day rent', 'excellent', 2),
('Disc Harrow Rent', 'rushabh@agzone.com', '9876543212', 800.00, 'Rajasthan', 'harrow.jpg', 'farmer_rushabh', 'rent', 'Soil conditioning equipment', '10 feet width', 'good', 3),
('Thresher Machine', 'srivatsan@agzone.com', '9876543213', 250000.00, 'Tamil Nadu', 'thresher.jpg', 'farmer_srivatsan', 'sell', 'Electric grain thresher', '2 HP, Capacity 1000 kg/hr', 'good', 4),
('Pump Set (Diesel)', 'anurag@agzone.com', '9876543214', 45000.00, 'Uttar Pradesh', 'pump.jpg', 'farmer_anurag', 'sell', 'High pressure water pump', '3 HP, 1000 L/hr', 'average', 5),
('Sprayer Machine', 'harsha@agzone.com', '9876543210', 25000.00, 'Karnataka', 'sprayer.jpg', 'farmer_harsha', 'sell', 'Knapsack sprayer for pesticide application', '16 L capacity', 'good', 1),
('Pulverizer Machine', 'akshay@agzone.com', '9876543211', 120000.00, 'Maharashtra', 'pulverizer.jpg', 'farmer_akshay', 'sell', 'Feed/grain pulverizer', '5 HP, 500 kg/hr', 'excellent', 2),
('Plough (Wooden)', 'rushabh@agzone.com', '9876543212', 12000.00, 'Rajasthan', 'plough.jpg', 'farmer_rushabh', 'sell', 'Traditional soil ploughing equipment', 'Single furrow', 'fair', 3),
('Seed Drill', 'srivatsan@agzone.com', '9876543213', 35000.00, 'Tamil Nadu', 'seeddrill.jpg', 'farmer_srivatsan', 'sell', 'Mechanical seed planting machine', '8 row capacity', 'good', 4),
('Trolley (Farm)', 'anurag@agzone.com', '9876543214', 50000.00, 'Uttar Pradesh', 'trolley.jpg', 'farmer_anurag', 'sell', 'Heavy-duty farm trolley', '3 ton capacity', 'good', 5);

-- Sample Contact Messages
INSERT INTO contacts (name, email, subject, message, phoneNumber, status) VALUES
('Harsha Kumar', 'harsha@example.com', 'Tractor Rental Query', 'I am interested in renting a tractor for my farm. Can you provide more details about availability?', '9876543210', 'new'),
('Akshay Miterani', 'akshay@example.com', 'Quality Complaint', 'I received tomatoes but some were damaged. Please replace them.', '9876543211', 'new'),
('Rushabh Mehta', 'rushabh@example.com', 'Price Negotiation', 'Can you reduce the price for bulk order of wheat?', '9876543212', 'read'),
('Srivatsan S', 'srivatsan@example.com', 'Service Inquiry', 'What are the shipping charges to Tamil Nadu?', '9876543213', 'replied'),
('Anurag Acharya', 'anurag@example.com', 'Product Information', 'Do you have organic vegetables available?', '9876543214', 'new'),
('Priya Singh', 'priya@example.com', 'Account Issue', 'I cannot login to my account. Can you help?', '9876543215', 'new');

-- Sample Orders
INSERT INTO orders (orderId, userId, totalAmount, status, paymentMethod, shippingAddress, contactNumber, notes) VALUES
('ORD-2025-001', 6, 15000.00, 'delivered', 'UPI', '123 Main Street, Delhi 110001', '9876543215', 'Delivered successfully'),
('ORD-2025-002', 7, 8500.00, 'shipped', 'Credit Card', '456 Park Avenue, Mumbai 400001', '9876543216', 'In transit'),
('ORD-2025-003', 8, 22300.00, 'confirmed', 'Debit Card', '789 Tech Park, Bangalore 560001', '9876543217', 'Payment received'),
('ORD-2025-004', 9, 5600.00, 'pending', 'Net Banking', '321 Temple Road, Hyderabad 500001', '9876543218', 'Awaiting confirmation'),
('ORD-2025-005', 6, 45000.00, 'cancelled', 'Credit Card', '123 Main Street, Delhi 110001', '9876543215', 'Cancelled by customer'),
('ORD-2025-006', 7, 12000.00, 'delivered', 'UPI', '456 Park Avenue, Mumbai 400001', '9876543216', 'Delivered on time');

-- Sample Cart Items
INSERT INTO cart_items (userId, cropId, machineId, itemName, price, quantity, itemType) VALUES
(6, 1, NULL, 'Premium Wheat', 2500.00, 5, 'crop'),
(6, 3, NULL, 'Fresh Tomatoes', 40.00, 100, 'crop'),
(7, 5, NULL, 'Onions', 30.00, 50, 'crop'),
(8, 7, NULL, 'Green Beans', 60.00, 20, 'crop'),
(9, 2, NULL, 'Basmati Rice', 5000.00, 10, 'crop'),
(6, NULL, 1, 'John Deere Tractor', 850000.00, 1, 'machine'),
(7, NULL, 2, 'Bajaj Tractor', 650000.00, 1, 'machine'),
(8, NULL, 10, 'Sprayer Machine', 25000.00, 2, 'machine');

-- Display sample counts
SELECT 'Users Created: ' AS Info, COUNT(*) FROM membership
UNION ALL
SELECT 'Crops Created: ', COUNT(*) FROM crops
UNION ALL
SELECT 'Machinery Posts Created: ', COUNT(*) FROM posts
UNION ALL
SELECT 'Contact Messages: ', COUNT(*) FROM contacts
UNION ALL
SELECT 'Orders Created: ', COUNT(*) FROM orders
UNION ALL
SELECT 'Cart Items: ', COUNT(*) FROM cart_items;
