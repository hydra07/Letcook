CREATE DATABASE t1
USE t1
drop database t1
-- Bảng CategoryProduct

select * from users
CREATE TABLE CategoryProduct ( --Rau , thịt , hải sản, ...
    CategoryId INT IDENTITY(1,1) PRIMARY KEY,
    CategoryName NVARCHAR(15) NOT NULL UNIQUE,
    img NVARCHAR(100) NULL UNIQUE,
	isEnabled bit default 1,
);

-- Bảng MeasurementUnit
CREATE TABLE MeasurementUnit ( --1kg , 1 lit , cái muỗng , ...
    MeasurementUnitId INT IDENTITY(1,1) PRIMARY KEY,
    MeasurementUnitName VARCHAR(50) NOT NULL,
    MeasurementUnitSymbol VARCHAR(10) NOT NULL,
    MeasurementUnitClass VARCHAR(20) NOT NULL,
	img NVARCHAR(100) NULL,
	isEnabled bit default 1,
);


-- Bảng Untensil
CREATE TABLE Untensil ( --Nồi chiên không dầu, lò nướng, ...
    UtensilId INT IDENTITY(1,1) PRIMARY KEY,
    UtensilName VARCHAR(50) NOT NULL UNIQUE,
	img NVARCHAR(100) NULL UNIQUE,
	isEnabled bit default 1,

);


-- Bảng DishCategory
CREATE TABLE DishCategory ( --Chay, salad, soup, ...
    DishCategoryId INT IDENTITY(1,1) PRIMARY KEY,
    DishCategoryName NVARCHAR(50) NOT NULL UNIQUE, 
	img NVARCHAR(100) NULL UNIQUE,
	isEnabled bit default 1,
);


-- Bảng RecipeLevel
CREATE TABLE RecipeLevel (--dễ trung bình khó 
    RecipeLevelId INT IDENTITY(1,1) PRIMARY KEY,
    LevelName NVARCHAR(20) UNIQUE
);


-- Bảng Users
CREATE TABLE Users (
    UserId INT IDENTITY(1,1) PRIMARY KEY,
    UserEmail NVARCHAR(50) NOT NULL UNIQUE,
	UserName nvarchar(50),
    UserPassword VARCHAR(50) NOT NULL,
    UserAvatar VARCHAR(100),
    UserPhone VARCHAR(20),
    UserAddress VARCHAR(100),
    isAdmin BIT DEFAULT 0,
	phone varchar(11)
);
select * from users
-- Bảng Product
CREATE TABLE Product ( --gà , cà rốt ,...
    ProductId INT IDENTITY(1,1) NOT NULL,
    ProductName NVARCHAR(200) NULL,
    CategoryId INT NULL,
    Price MONEY NULL,
    UnitsInStock SMALLINT NULL,
    Origin NVARCHAR(30) NULL,
    ProductDetail NVARCHAR(MAX) NULL,
    CONSTRAINT PK_Products PRIMARY KEY CLUSTERED (ProductId),
    CONSTRAINT FK_Products FOREIGN KEY (CategoryId) REFERENCES CategoryProduct (CategoryId),
	isEnabled bit default 1,
);

-- Bảng ProductImage
CREATE TABLE ProductImage (
    ImageId INT IDENTITY(1,1) NOT NULL,
    ProductId INT NOT NULL,
    ImageName NVARCHAR(100),
    CONSTRAINT PK_ProductImages PRIMARY KEY CLUSTERED (ImageId),
    CONSTRAINT FK_ProductImages_Products FOREIGN KEY (ProductId) REFERENCES Product (ProductId)
);


-- Bảng Recipe
CREATE TABLE Recipe (
    RecipeId INT IDENTITY(1,1) PRIMARY KEY,
    RecipeName NVARCHAR(50) NOT NULL,
    RecipeDescription NVARCHAR(200),
    RecipeLevelId INT,
    RecipeExcellent INT,
    RecipeLove INT,
    RecipeBad INT,
    RecipeAuthor INT,
	IsAccepted BIT default 0,
    CONSTRAINT FK_Recipe_Users FOREIGN KEY (RecipeAuthor) REFERENCES Users (UserId),
    CONSTRAINT FK_Recipe_Level FOREIGN KEY (RecipeLevelId) REFERENCES RecipeLevel (RecipeLevelId),
	isEnable bit default 1,
);

-- Bảng RecipeCategory: một công thức thì có nhiều category 
CREATE TABLE RecipeCategory ( 
	RecipeCategoryId INT IDENTITY(1,1) PRIMARY KEY,
    RecipeId INT NOT NULL,
    DishCategoryId INT NOT NULL,
    CONSTRAINT FK_RecipeCategory_Recipe FOREIGN KEY (RecipeId) REFERENCES Recipe (RecipeId),
    CONSTRAINT FK_RecipeCategory_DishCategory FOREIGN KEY (DishCategoryId) REFERENCES DishCategory (DishCategoryId)
);


-- Bảng RecipeUntensil
CREATE TABLE RecipeUntensil ( 
    RecipeUntensilId INT IDENTITY(1,1) PRIMARY KEY,
    RecipeUtensilRecipe INT,
    RecipeUtensilUtensil INT,
    CONSTRAINT FK_RecipeUntensil_Recipe FOREIGN KEY (RecipeUtensilRecipe) REFERENCES Recipe (RecipeId),
    CONSTRAINT FK_RecipeUntensil_Utensil FOREIGN KEY (RecipeUtensilUtensil) REFERENCES Untensil (UtensilId)
);

-- Bảng RecipeStep : từng bước của công thức
CREATE TABLE RecipeStep (
    RecipeStepId INT IDENTITY(1,1) PRIMARY KEY,
    RecipeId INT,
    RecipeDescription VARCHAR(200),
    RecipeImage NVARCHAR(100),
    CONSTRAINT FK_RecipeStep_Recipe FOREIGN KEY (RecipeId) REFERENCES Recipe (RecipeId)
);

-- Bảng RecipeStepImg :ảnh của từng bước
CREATE TABLE RecipeStepImg (
    RecipeStepImgId INT IDENTITY(1,1) PRIMARY KEY,
    RecipeStepId INT NOT NULL,
    ImageName NVARCHAR(100),
    CONSTRAINT FK_RecipeStepImg_RecipeStep FOREIGN KEY (RecipeStepId) REFERENCES RecipeStep (RecipeStepId)
);

-- Bảng RecipeComment
CREATE TABLE RecipeComment (
    CommentId INT IDENTITY(1,1) PRIMARY KEY,
    RecipeId INT NOT NULL,
    UserId INT NOT NULL,
    Content NVARCHAR(MAX) NOT NULL,
    Timestamp DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_RecipeComment_Recipes FOREIGN KEY (RecipeId) REFERENCES Recipe (RecipeId),
    CONSTRAINT FK_RecipeComment_Users FOREIGN KEY (UserId) REFERENCES Users (UserId)
);


-- Bảng RecipeProduct: các nguyên liệu có trong công thức
CREATE TABLE RecipeProduct (
    RecipeProductId INT IDENTITY(1,1) PRIMARY KEY,
    RecipeId INT,
    Quantity FLOAT,
    ProductId INT, 
	MeasurementUnitId INT,
    CONSTRAINT FK_RecipeProduct_Recipe FOREIGN KEY (RecipeId) REFERENCES Recipe (RecipeId),
    CONSTRAINT FK_RecipeProduct_Product FOREIGN KEY (ProductId) REFERENCES Product (ProductId),
    CONSTRAINT FK_RecipeProduct_MeasurementUnit FOREIGN KEY (MeasurementUnitId) REFERENCES MeasurementUnit (MeasurementUnitId)
);


-- Bảng Carts
CREATE TABLE Carts (
    CartId INT IDENTITY(1,1) PRIMARY KEY,
    UserId INT NOT NULL,
    CONSTRAINT FK_Carts_Users FOREIGN KEY (UserId) REFERENCES Users (UserId)
);

-- Bảng CartItems
CREATE TABLE CartItems (
    CartItemId INT IDENTITY(1,1) PRIMARY KEY,
    CartId INT NOT NULL,
    ProductId INT NOT NULL,
    Quantity SMALLINT NOT NULL,
    CONSTRAINT FK_CartItems_Carts FOREIGN KEY (CartId) REFERENCES Carts (CartId),
    CONSTRAINT FK_CartItems_Products FOREIGN KEY (ProductId) REFERENCES Product (ProductId)
);


-- Bảng Orders
CREATE TABLE Orders (
    OrderId INT IDENTITY(1,1) NOT NULL,
    UserId INT NOT NULL,
    OrderDate DATETIME NULL,
    ShippedDate DATETIME NULL,
    ShipAddress NVARCHAR(60) NULL,
    TotalMoney MONEY NULL,
    OrderStatus SMALLINT NULL,
    IsFeedbacked BIT NULL,
    CONSTRAINT PK_Orders PRIMARY KEY CLUSTERED (OrderId),
    CONSTRAINT FK_Orders_Users FOREIGN KEY (UserId) REFERENCES Users (UserId)
);

-- Bảng OrderDetail
CREATE TABLE OrderDetail (
    OrderId INT NOT NULL,
    ProductId INT NOT NULL,
    UnitPrice MONEY NOT NULL,
    Quantity SMALLINT NOT NULL,
    CONSTRAINT PK_OrderLine PRIMARY KEY CLUSTERED (OrderId, ProductId),
    CONSTRAINT FK_OrderDetails_Orders FOREIGN KEY (OrderId) REFERENCES Orders (OrderId),
    CONSTRAINT FK_OrderDetails_Products FOREIGN KEY (ProductId) REFERENCES Product (ProductId)
);



-- Bảng Feedback
CREATE TABLE Feedback (
    FeedbackId INT IDENTITY(1,1) PRIMARY KEY,
    ProductId INT NOT NULL,
    Content NVARCHAR(MAX) NOT NULL,
    UserId INT NOT NULL,
    Star INT NOT NULL,
    CreatedAt DATETIME NULL,
    CONSTRAINT FK_Feedbacks_Products FOREIGN KEY (ProductId) REFERENCES Product (ProductId),
    CONSTRAINT FK_Feedbacks_Users FOREIGN KEY (UserId) REFERENCES Users (UserId)
);



-- Bảng Notification : thông báo đơn hàng
CREATE TABLE Notifications (
    NotificationId INT IDENTITY(1,1) NOT NULL,
    UserId INT NOT NULL,
    Title NVARCHAR(200) NOT NULL,
    Message NVARCHAR(500) NOT NULL,
    CreatedAt DATETIME NOT NULL DEFAULT GETDATE(),
    Type SMALLINT NOT NULL,
    OrderId INT,
    CONSTRAINT PK_Notifications PRIMARY KEY CLUSTERED (NotificationId),
    CONSTRAINT FK_Notifications_Users FOREIGN KEY (UserId) REFERENCES Users (UserId),
    CONSTRAINT FK_Notifications_Orders FOREIGN KEY (OrderId) REFERENCES Orders (OrderId)
);

-- Thiếu phần thông báo cho công thức: ví dụ công thức của bạn không được chấp nhận, ....


-- Bảng Cooksnap
CREATE TABLE Cooksnap (
    CooksnapId INT IDENTITY(1,1) PRIMARY KEY,
    CooksnapImage NVARCHAR(100) NOT NULL,
    CooksnapDate DATETIME DEFAULT GETDATE(),
    RecipeId INT,
    UserId INT,
    CONSTRAINT FK_Cooksnap_Recipe FOREIGN KEY (RecipeId) REFERENCES Recipe (RecipeId),
    CONSTRAINT FK_Cooksnap_User FOREIGN KEY (UserId) REFERENCES Users (UserId)
);

CREATE TABLE Nutrition(
	NutritionId INT IDENTITY (1,1) PRIMARY KEY,
	NutritionName NVARCHAR(50) UNIQUE,
	img NVARCHAR(100) NULL,
	isEnabled bit default 1,
)

CREATE TABLE ProductNutrition(
	ProductNutritionId INT IDENTITY(1,1) PRIMARY KEY,
	ProductId INT,
	NutritionId INT,
	MeasurementUnitId INT,
    Quantity FLOAT,
    CONSTRAINT FK_ProductNutrition_Product FOREIGN KEY (ProductId) REFERENCES Product (ProductId),
    CONSTRAINT FK_ProductNutrition_MeasurementUnit FOREIGN KEY (MeasurementUnitId) REFERENCES MeasurementUnit (MeasurementUnitId),
	CONSTRAINT FK_ProductNutrition_Nutrition FOREIGN KEY (NutritionId) REFERENCES Nutrition (NutritionId),
)

-- Bảng SavedRecipe
CREATE TABLE SavedRecipe (
    SavedRecipeId INT IDENTITY(1,1) PRIMARY KEY,
    UserId INT,
    RecipeId INT,
    CONSTRAINT FK_SavedRecipe_User FOREIGN KEY (UserId) REFERENCES Users (UserId),
    CONSTRAINT FK_SavedRecipe_Recipe FOREIGN KEY (RecipeId) REFERENCES Recipe (RecipeId)
);
	