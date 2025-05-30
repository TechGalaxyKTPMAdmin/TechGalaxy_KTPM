USE [master]
GO
/****** Object:  Database [NotificationDB]    Script Date: 18/05/2025 8:14:01 CH ******/
CREATE DATABASE [NotificationDB]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'NotificationDB', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\NotificationDB.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'NotificationDB_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\NotificationDB_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [NotificationDB] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [NotificationDB].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [NotificationDB] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [NotificationDB] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [NotificationDB] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [NotificationDB] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [NotificationDB] SET ARITHABORT OFF 
GO
ALTER DATABASE [NotificationDB] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [NotificationDB] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [NotificationDB] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [NotificationDB] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [NotificationDB] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [NotificationDB] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [NotificationDB] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [NotificationDB] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [NotificationDB] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [NotificationDB] SET  DISABLE_BROKER 
GO
ALTER DATABASE [NotificationDB] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [NotificationDB] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [NotificationDB] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [NotificationDB] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [NotificationDB] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [NotificationDB] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [NotificationDB] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [NotificationDB] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [NotificationDB] SET  MULTI_USER 
GO
ALTER DATABASE [NotificationDB] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [NotificationDB] SET DB_CHAINING OFF 
GO
ALTER DATABASE [NotificationDB] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [NotificationDB] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [NotificationDB] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [NotificationDB] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [NotificationDB] SET QUERY_STORE = ON
GO
ALTER DATABASE [NotificationDB] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [NotificationDB]
GO
/****** Object:  Table [dbo].[email_logs]    Script Date: 18/05/2025 8:14:01 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[email_logs](
	[id] [varchar](255) NOT NULL,
	[email] [varchar](255) NOT NULL,
	[order_id] [varchar](255) NOT NULL,
	[sent_at] [datetime2](6) NOT NULL,
	[status] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[email_logs] ([id], [email], [order_id], [sent_at], [status]) VALUES (N'106bacbe-bd1f-4259-9dcd-819a2daa09d2', N'ggduck@gmail.com', N'45f69b24-4460-4d91-872d-4bc73810ef9d', CAST(N'2025-03-16T10:51:29.8838150' AS DateTime2), N'PAYMENT_PAID')
INSERT [dbo].[email_logs] ([id], [email], [order_id], [sent_at], [status]) VALUES (N'149c84d5-d1bb-4db0-9fc6-87305e61d2df', N'ggduck@gmail.com', N'13b5b0ca-3c65-4968-83cb-442a7ae036e8', CAST(N'2025-03-19T22:09:14.9023160' AS DateTime2), N'PAYMENT_PAID')
INSERT [dbo].[email_logs] ([id], [email], [order_id], [sent_at], [status]) VALUES (N'1b90e2df-0814-4941-a06f-29bf8be8fd57', N'ggducvu@gmail.com', N'5f567f8e-61e3-4743-b796-ad15f8a076b1', CAST(N'2025-04-25T14:54:57.0387510' AS DateTime2), N'PAYMENT_PAID')
INSERT [dbo].[email_logs] ([id], [email], [order_id], [sent_at], [status]) VALUES (N'1d2feb23-ea69-4b3f-8369-8b29f0eb8b9d', N'ggducvu@gmail.com', N'7fb9717f-00b5-4c28-985f-7c12d5510786', CAST(N'2025-05-18T03:31:27.3092640' AS DateTime2), N'PAYMENT_PAID')
INSERT [dbo].[email_logs] ([id], [email], [order_id], [sent_at], [status]) VALUES (N'1f899648-b625-432a-afaa-39138f95de52', N'ggducvu@gmail.com', N'40ce6da7-e73b-460f-8890-4ea5a102fbeb', CAST(N'2025-04-26T12:52:40.2014420' AS DateTime2), N'PAYMENT_PAID')
INSERT [dbo].[email_logs] ([id], [email], [order_id], [sent_at], [status]) VALUES (N'278abd30-fb13-44d0-be33-14be9a633c2c', N'ggduck@gmail.com', N'7dace794-d8f7-4c24-8c01-bbfc66cba7a3', CAST(N'2025-03-16T10:59:12.3887260' AS DateTime2), N'PAYMENT_PAID')
INSERT [dbo].[email_logs] ([id], [email], [order_id], [sent_at], [status]) VALUES (N'337af9f2-3744-4184-90ab-7cca18089a4d', N'ggduck@gmail.com', N'e2313867-eb3e-49d9-bedd-63c3d10c06e1', CAST(N'2025-04-23T15:20:55.7062840' AS DateTime2), N'PAYMENT_PAID')
INSERT [dbo].[email_logs] ([id], [email], [order_id], [sent_at], [status]) VALUES (N'3b1e8776-de83-4439-8d5f-ce2d878499f1', N'ggduck@gmail.com', N'b89f94d4-3ff1-4185-9cd3-9938ef2b7eb3', CAST(N'2025-03-16T22:20:31.7857170' AS DateTime2), N'PAYMENT_PAID')
INSERT [dbo].[email_logs] ([id], [email], [order_id], [sent_at], [status]) VALUES (N'53744d6c-49a5-40ee-b85a-6a87d96bfa47', N'ggduck@gmail.com', N'657d2c8e-e2a1-40a1-90e7-5c5742a3e5c7', CAST(N'2025-03-17T20:51:08.5175060' AS DateTime2), N'PAYMENT_PAID')
INSERT [dbo].[email_logs] ([id], [email], [order_id], [sent_at], [status]) VALUES (N'53d8cdb9-6fcd-4eb5-b3c2-131a4bdec287', N'ggduck@gmail.com', N'c459d41f-80c5-444c-8975-11e7aa879145', CAST(N'2025-03-16T22:18:10.3164260' AS DateTime2), N'PAYMENT_PAID')
INSERT [dbo].[email_logs] ([id], [email], [order_id], [sent_at], [status]) VALUES (N'5c740035-1a32-40f1-8caa-de30ac84e689', N'ggducvu@gmail.com', N'bad08899-3e4d-4c0e-97e8-152bc60e47c5', CAST(N'2025-05-18T06:43:52.4359600' AS DateTime2), N'PAYMENT_PAID')
INSERT [dbo].[email_logs] ([id], [email], [order_id], [sent_at], [status]) VALUES (N'86c09d2e-3e6d-44e0-8c29-9ea3ef1e71ae', N'ggducvu@gmail.com', N'2c8a6c25-7697-4c70-9c31-b038646e9325', CAST(N'2025-05-18T06:21:34.6202010' AS DateTime2), N'PAYMENT_PAID')
INSERT [dbo].[email_logs] ([id], [email], [order_id], [sent_at], [status]) VALUES (N'882fbd59-a114-48d5-af4e-10c862bbce17', N'ggduck@gmail.com', N'dfbd03ea-2756-4428-953b-52598cd94ceb', CAST(N'2025-03-16T10:51:25.4875670' AS DateTime2), N'PAYMENT_PAID')
INSERT [dbo].[email_logs] ([id], [email], [order_id], [sent_at], [status]) VALUES (N'9995129c-2ce0-456b-94e6-fd0be4472c5d', N'ggducvu@gmail.com', N'a94860f4-19ba-4b50-8200-ee388747418c', CAST(N'2025-04-25T14:32:23.4839250' AS DateTime2), N'PAYMENT_PAID')
INSERT [dbo].[email_logs] ([id], [email], [order_id], [sent_at], [status]) VALUES (N'acc5b974-1583-4a96-bab7-c197f6803ee7', N'ggduck@gmail.com', N'0246646d-ec53-43b6-85f7-0b34916771c4', CAST(N'2025-03-16T11:02:11.2726720' AS DateTime2), N'PAYMENT_PAID')
INSERT [dbo].[email_logs] ([id], [email], [order_id], [sent_at], [status]) VALUES (N'b0f9ed2e-ff97-493c-80bf-84e5ba05de8f', N'ggducvu@gmail.com', N'629683ba-8fb9-49e3-b893-94d1c2438ed3', CAST(N'2025-05-18T07:13:49.8603150' AS DateTime2), N'PAYMENT_PAID')
INSERT [dbo].[email_logs] ([id], [email], [order_id], [sent_at], [status]) VALUES (N'f3791809-e68c-4711-956e-904e752080ae', N'ggduck@gmail.com', N'b7d920f7-1aff-4941-8517-ec4d6eda2a67', CAST(N'2025-03-16T22:32:47.9832950' AS DateTime2), N'PAYMENT_PAID')
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UK5adi12oypyuelqadf2o29tquy]    Script Date: 18/05/2025 8:14:01 CH ******/
ALTER TABLE [dbo].[email_logs] ADD  CONSTRAINT [UK5adi12oypyuelqadf2o29tquy] UNIQUE NONCLUSTERED 
(
	[order_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[email_logs]  WITH CHECK ADD CHECK  (([status]='PAYMENT_FAILED' OR [status]='PAYMENT_PAID'))
GO
USE [master]
GO
ALTER DATABASE [NotificationDB] SET  READ_WRITE 
GO
