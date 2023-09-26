/*
CREATE TABLE Users (
    user_id INT IDENTITY(1,1) PRIMARY KEY,
    user_name NVARCHAR(50) NOT NULL UNIQUE,
    user_email NVARCHAR(50) NOT NULL,
    user_password VARCHAR(50) NOT NULL,
    user_avatar VARCHAR(100),
    user_phone VARCHAR(20),
    user_address VARCHAR(100),
    user_premium BIT DEFAULT 0
);


Bang danh muc san pham
CREATE TABLE CategoryProduct (
    category_id INT IDENTITY(1,1) PRIMARY KEY,
    category_name NVARCHAR(15) NOT NULL,
    picture NVARCHAR(100) NULL,
	--picture NVARCHAR(100) path,
);

--san pham duoc ban
CREATE TABLE Product (
    product_id INT IDENTITY(1,1) NOT NULL,
    product_name NVARCHAR(200) NULL,
    category_id INT NULL,
    price MONEY NULL,
    units_in_stock SMALLINT NULL,
    origin NVARCHAR(30) NULL,
    product_detail NVARCHAR(MAX) NULL,
    CONSTRAINT PK_Products PRIMARY KEY CLUSTERED (product_id),
    CONSTRAINT FK_Products FOREIGN KEY (category_id) REFERENCES CategoryProduct (category_id)
);

Bang hinh anh cua san pham
CREATE TABLE ProductImage (
    image_id INT IDENTITY(1,1) NOT NULL,
    product_id INT NOT NULL,
	image_name, NVARCHAR(100),
    --image_path NVARCHAR(100),
    CONSTRAINT PK_ProductImages PRIMARY KEY CLUSTERED (image_id),
    CONSTRAINT FK_ProductImages_Products FOREIGN KEY (product_id) REFERENCES Products (product_id)
);

--bang don hang
CREATE TABLE Orders (
    order_id INT IDENTITY(1,1) NOT NULL,
    user_id INT NOT NULL,
    order_date DATETIME NULL,
    shipped_date DATETIME NULL,
    ship_address NVARCHAR(60) NULL,
    total_money MONEY NULL,
    order_status SMALLINT NULL,
    is_feedbacked BIT 0,
    CONSTRAINT PK_Orders PRIMARY KEY CLUSTERED (order_id),
    CONSTRAINT FK_Orders_Users FOREIGN KEY (user_id) REFERENCES Users (user_id)
);



--Bang chi tiet don hang
CREATE TABLE OrderDetail (
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    unit_price MONEY NOT NULL,
    quantity SMALLINT NOT NULL,
    CONSTRAINT PK_OrderLine PRIMARY KEY CLUSTERED (order_id, product_id),
    CONSTRAINT FK_OrderDetails_Orders FOREIGN KEY (order_id) REFERENCES Orders (order_id),
    CONSTRAINT FK_OrderDetails_Products FOREIGN KEY (product_id) REFERENCES Products (product_id)
);

--Bang binh luan va danh gia cua san pham
CREATE TABLE Feedback (
    feedback_id INT IDENTITY(1,1) PRIMARY KEY,
    product_id INT NOT NULL,
    content NVARCHAR(MAX) NOT NULL,
    user_id INT NOT NULL,
    star INT NOT NULL,
    created_at DATETIME NULL,
    CONSTRAINT FK_Feedbacks_Products FOREIGN KEY (product_id) REFERENCES Products (product_id),
    CONSTRAINT FK_Feedbacks_Users FOREIGN KEY (user_id) REFERENCES Users (user_id)
);


--Bang thong bao ve don hang
CREATE TABLE Notification (
    notification_id INT IDENTITY(1,1) NOT NULL,
    user_id INT NOT NULL,
    title NVARCHAR(200) NOT NULL,
    message NVARCHAR(500) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    type SMALLINT NOT NULL,
    order_id INT,
    CONSTRAINT PK_Notifications PRIMARY KEY CLUSTERED (notification_id),
    CONSTRAINT FK_Notifications_Users FOREIGN KEY (user_id) REFERENCES Users (user_id),
    CONSTRAINT FK_Notifications_Orders FOREIGN KEY (order_id) REFERENCES Orders (order_id)
);

-- Bảng giỏ hàng
CREATE TABLE Carts (
    cart_id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT NOT NULL,
    CONSTRAINT FK_Carts_Users FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

-- Bảng các mặt hàng trong giỏ hàng
CREATE TABLE CartItems (
    cart_item_id INT IDENTITY(1,1) PRIMARY KEY,
    cart_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity SMALLINT NOT NULL,
    CONSTRAINT FK_CartItems_Carts FOREIGN KEY (cart_id) REFERENCES Carts(cart_id),
    CONSTRAINT FK_CartItems_Products FOREIGN KEY (product_id) REFERENCES Products(product_id)
);


CREATE TABLE RecipeLevel( --don gian , trung binh , kho, ....
	recipe_level_id INT IDENTITY(1,1) PRIMARY KEY,
	level_name NVARCHAR(20) PRIMARY KEY,
)

--bang cong thuc
CREATE TABLE Recipe (
    recipe_id INT IDENTITY(1,1) PRIMARY KEY,
    recipe_name NVARCHAR(50) NOT NULL,
	recipe_description NVARCHAR(200),
	recipe_level_id INT,
	recipe_spice NVARCHAR(100), --gia vi muoi , duong , tieu...
    recipe_excellent INT,
    recipe_love INT,
    recipe_bad INT,
    recipe_author INT,
    CONSTRAINT FK_Recipe_Users FOREIGN KEY (recipe_author) REFERENCES Users (user_id),
	CONSTRAINT FK_Recipe_level FOREIGN KEY (level_id) REFERENCES Level (recipe_level_id)
);

--cac dung cu nau an
CREATE TABLE Untensil (
    utensil_id INT IDENTITY(1,1) PRIMARY KEY,
    utensil_name VARCHAR(50) NOT NULL
);

--cac dung cu can thiet cua mon an
CREATE TABLE RecipeUntensil (
    recipe_utensil_id INT IDENTITY(1,1) PRIMARY KEY,
    recipe_utensil_recipe INT FOREIGN KEY REFERENCES Recipe(recipe_id),
    recipe_utensil_utensil INT FOREIGN KEY REFERENCES Untensil(utensil_id)
);


--Tung buoc cua cong thuc
CREATE TABLE RecipeStep (
    recipe_step_id INT IDENTITY(1,1) PRIMARY KEY,
    recipe_id INT,
    recipe_description VARCHAR(200),
    recipe_image NVARCHAR(100),
    CONSTRAINT FK_RecipeStep_Recipe FOREIGN KEY (recipe_id) REFERENCES Recipe (recipe_id)
);
--anh cua buoc huong dan
CREATE TABLE RecipeStepImg(
	RSI_id INT IDENTITY(1,1) PRIMARY KEY,
    RecipeStep_id INT NOT NULL,
	image_name, NVARCHAR(100),
  --image_path NVARCHAR(100),
    CONSTRAINT FK_RecipeStepImg_RecipeStep FOREIGN KEY (RecipeStep_id) REFERENCES RecipeStep (RecipeStep_id)
)
  
--binh luan ve cong thuc
CREATE TABLE RecipeComment (
    comment_id INT IDENTITY(1,1) PRIMARY KEY,
    recipe_id INT NOT NULL,
    user_id INT NOT NULL,
    content NVARCHAR(MAX) NOT NULL,
    timestamp DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_Comments_Recipes FOREIGN KEY (recipe_id) REFERENCES Recipes (recipe_id),
    CONSTRAINT FK_Comments_Users FOREIGN KEY (user_id) REFERENCES Users (user_id)
);

CREATE TABLE CategoryRecipe (
    category_id INT IDENTITY(1,1) PRIMARY KEY,
    category_name NVARCHAR(50) NOT NULL
);

CREATE TABLE RecipeCategory (
    recipe_id INT NOT NULL,
    category_id INT NOT NULL,
    CONSTRAINT FK_RecipeCategories_Recipes FOREIGN KEY (recipe_id) REFERENCES Recipes (recipe_id),
    CONSTRAINT FK_RecipeCategories_Categories FOREIGN KEY (category_id) REFERENCES CategoryRecipe (category_id)
);

CREATE TABLE MeasurementUnit ( --mg, kg, lit , quả, cái, 
    unit_id INT IDENTITY(1,1) PRIMARY KEY,
    unit_name VARCHAR(50) NOT NULL,
    unit_class VARCHAR(20) NOT NULL
);



Bang chua cac san pham co trong cong thuc
CREATE TABLE RecipeProduct (
    rp_id INT IDENTITY(1,1) PRIMARY KEY,
    recipe_id INT,
    rp_measurement_unit_id INT, 
    rp_product_quantity FLOAT,
    product_id INT,
    CONSTRAINT FK_RecipeProduct_Recipe FOREIGN KEY (recipe_id) REFERENCES Recipe (recipe_id),
    CONSTRAINT FK_RecipeProduct_Product FOREIGN KEY (product_id) REFERENCES Products (product_id),
    CONSTRAINT FK_RecipeProduct_MeasurementUnit FOREIGN KEY (rp_measurement_unit_id) REFERENCES MeasurementUnit (unit_id)
);




Mon an cua nguoi dung duoc nau theo cong thuc
CREATE TABLE Cooksnap (
    cooksnap_id INT IDENTITY(1,1) PRIMARY KEY,
    cooksnap_image NVARCHAR(100) NOT NULL,
	--cooksnap_image_path NVARCHAR(100) NOT NULL,
    cooksnap_date DATETIME DEFAULT GETDATE(),
    cooksnap_recipe INT,
    cooksnap_user INT,
    CONSTRAINT FK_Cooksnap_Recipe FOREIGN KEY (cooksnap_recipe) REFERENCES Recipe (recipe_id),
    CONSTRAINT FK_Cooksnap_User FOREIGN KEY (cooksnap_user) REFERENCES Users (user_id)
);



Bang cac cong thuc ma nguoi dung da luu
CREATE TABLE SavedRecipe (
    saved_recipe_id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT,
    recipe_id INT,
    CONSTRAINT FK_SavedRecipe_User FOREIGN KEY (user_id) REFERENCES Users (user_id),
    CONSTRAINT FK_SavedRecipe_Recipe FOREIGN KEY (recipe_id) REFERENCES Recipe (recipe_id)
);



*/
