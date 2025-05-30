USE [master]
GO
/****** Object:  Database [UserDB]    Script Date: 18/05/2025 8:12:20 CH ******/
CREATE DATABASE [UserDB]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'UserDB', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\UserDB.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'UserDB_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\UserDB_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [UserDB] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [UserDB].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [UserDB] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [UserDB] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [UserDB] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [UserDB] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [UserDB] SET ARITHABORT OFF 
GO
ALTER DATABASE [UserDB] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [UserDB] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [UserDB] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [UserDB] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [UserDB] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [UserDB] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [UserDB] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [UserDB] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [UserDB] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [UserDB] SET  DISABLE_BROKER 
GO
ALTER DATABASE [UserDB] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [UserDB] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [UserDB] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [UserDB] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [UserDB] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [UserDB] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [UserDB] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [UserDB] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [UserDB] SET  MULTI_USER 
GO
ALTER DATABASE [UserDB] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [UserDB] SET DB_CHAINING OFF 
GO
ALTER DATABASE [UserDB] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [UserDB] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [UserDB] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [UserDB] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [UserDB] SET QUERY_STORE = ON
GO
ALTER DATABASE [UserDB] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [UserDB]
GO
/****** Object:  Table [dbo].[account_role]    Script Date: 18/05/2025 8:12:21 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[account_role](
	[account_id] [varchar](255) NOT NULL,
	[role_id] [varchar](255) NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[accounts]    Script Date: 18/05/2025 8:12:21 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[accounts](
	[id] [varchar](255) NOT NULL,
	[created_at] [datetime2](6) NULL,
	[email] [varchar](255) NOT NULL,
	[password] [varchar](255) NOT NULL,
	[refresh_token] [text] NULL,
	[updated_at] [datetime2](6) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[blacklisted_tokens]    Script Date: 18/05/2025 8:12:21 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[blacklisted_tokens](
	[id] [varchar](255) NOT NULL,
	[expiry_date] [datetimeoffset](6) NOT NULL,
	[token] [nvarchar](1000) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[customers]    Script Date: 18/05/2025 8:12:21 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[customers](
	[id] [varchar](255) NOT NULL,
	[avatar] [varchar](255) NULL,
	[created_at] [datetime2](6) NULL,
	[date_of_birth] [date] NULL,
	[gender] [varchar](255) NULL,
	[name] [varchar](255) NOT NULL,
	[phone] [varchar](50) NULL,
	[updated_at] [datetime2](6) NULL,
	[user_status] [varchar](255) NOT NULL,
	[account_id] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[permission_role]    Script Date: 18/05/2025 8:12:21 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[permission_role](
	[role_id] [varchar](255) NOT NULL,
	[permission_id] [varchar](255) NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[permissions]    Script Date: 18/05/2025 8:12:21 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[permissions](
	[id] [varchar](255) NOT NULL,
	[api_path] [varchar](255) NOT NULL,
	[created_at] [datetime2](6) NULL,
	[created_by] [varchar](255) NULL,
	[method] [varchar](255) NOT NULL,
	[module] [varchar](255) NOT NULL,
	[name] [varchar](255) NOT NULL,
	[updated_at] [datetime2](6) NULL,
	[updated_by] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[roles]    Script Date: 18/05/2025 8:12:21 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[roles](
	[id] [varchar](255) NOT NULL,
	[active] [bit] NOT NULL,
	[created_at] [datetime2](6) NULL,
	[description] [text] NULL,
	[name] [varchar](255) NOT NULL,
	[updated_at] [datetime2](6) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[system_users]    Script Date: 18/05/2025 8:12:21 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[system_users](
	[id] [varchar](255) NOT NULL,
	[address] [varchar](255) NULL,
	[avatar] [varchar](255) NULL,
	[created_at] [datetime2](6) NULL,
	[gender] [varchar](255) NULL,
	[level] [varchar](255) NOT NULL,
	[name] [varchar](255) NOT NULL,
	[phone] [varchar](50) NULL,
	[system_user_status] [varchar](255) NOT NULL,
	[updated_at] [datetime2](6) NULL,
	[account_id] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[user_actions]    Script Date: 18/05/2025 8:12:21 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[user_actions](
	[id] [varchar](255) NOT NULL,
	[action_details] [varchar](255) NULL,
	[action_type] [varchar](255) NULL,
	[created_at] [datetime2](6) NULL,
	[product_id] [varchar](255) NULL,
	[search_query] [varchar](255) NULL,
	[updated_at] [datetime2](6) NULL,
	[customer_id] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'95070376-aa41-474a-ac1f-d10d7463d030', N'd176139a-dbb0-42e3-9ede-fb237028f740')
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'b3da5aba-545f-4c17-937c-f7a8b01200b4', N'ca01f704-b5fc-49d0-8370-a799f44fe28f')
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'fad7775d-441f-4d82-854b-aa17064267ad', N'ca01f704-b5fc-49d0-8370-a799f44fe28f')
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'a5b9fdcb-1999-4862-90a0-2a449b22d9da', N'ca01f704-b5fc-49d0-8370-a799f44fe28f')
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'cf890bc9-55eb-4adc-915a-40aa62fa2cc3', N'ca01f704-b5fc-49d0-8370-a799f44fe28f')
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'25a6782f-3807-44a4-a3e8-8645c175e232', N'ca01f704-b5fc-49d0-8370-a799f44fe28f')
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'66858ecc-2a49-4771-9391-6b3799d9810e', N'ca01f704-b5fc-49d0-8370-a799f44fe28f')
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'13470069-458d-43de-af84-84c1ed593491', N'ca01f704-b5fc-49d0-8370-a799f44fe28f')
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'b70c29da-7ea1-4d47-9d19-d83e2028c5ca', N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e')
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'8b30f4ac-5637-4a7a-994c-56205866ec10', N'ca01f704-b5fc-49d0-8370-a799f44fe28f')
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'ec6b93df-9b3a-4fa3-a41c-119ff5fd27b3', N'ca01f704-b5fc-49d0-8370-a799f44fe28f')
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'35c5e35d-3f2f-48c3-90d1-7b35c24cbdf0', N'd176139a-dbb0-42e3-9ede-fb237028f740')
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'd02fab0b-ffb5-4da4-bb5e-1ac8f48e4eee', N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e')
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'd02fab0b-ffb5-4da4-bb5e-1ac8f48e4eee', N'd176139a-dbb0-42e3-9ede-fb237028f740')
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'42be33a1-5193-4601-b14b-d04f667bad42', N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e')
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'06453093-9308-42ea-a9f2-0e91cc7a6109', N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e')
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'69ae1c22-01b5-4947-a090-5b7750d0682f', N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e')
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'71156581-0fb9-4072-9cad-6ef4dd4cd21a', N'ca01f704-b5fc-49d0-8370-a799f44fe28f')
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'51db70a0-1b4a-4410-a062-356febfdaf0a', N'ca01f704-b5fc-49d0-8370-a799f44fe28f')
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'f94f1160-de09-4f7d-a527-da5d7117079b', N'ca01f704-b5fc-49d0-8370-a799f44fe28f')
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'f2d0eb38-cdb0-4177-a359-cfc839068b26', N'ca01f704-b5fc-49d0-8370-a799f44fe28f')
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'e80215f7-9d0f-4651-b8f1-6e68d5c4d43b', N'ca01f704-b5fc-49d0-8370-a799f44fe28f')
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'709c8dca-b2d9-4c8f-b87c-a324ebf6f626', N'ca01f704-b5fc-49d0-8370-a799f44fe28f')
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'3347ff71-9465-4b08-b9ae-b8c8ae6fae0a', N'ca01f704-b5fc-49d0-8370-a799f44fe28f')
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'84f37ab0-feb4-4336-908a-48d52cfae4c5', N'ca01f704-b5fc-49d0-8370-a799f44fe28f')
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'5846a023-223a-4208-8dfc-dd909ee5dee7', N'ca01f704-b5fc-49d0-8370-a799f44fe28f')
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'0a093d20-4fae-47b2-8a27-65f1d51b0b48', N'ca01f704-b5fc-49d0-8370-a799f44fe28f')
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'59d99668-ac46-4c9e-9a0f-641292f662d2', N'ca01f704-b5fc-49d0-8370-a799f44fe28f')
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'7afc4e87-d4eb-4c58-a901-9ad9e920367e', N'ca01f704-b5fc-49d0-8370-a799f44fe28f')
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'029c8725-9ab0-409d-8198-a393bdfafd20', N'ca01f704-b5fc-49d0-8370-a799f44fe28f')
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'a901eb86-f5ad-4702-ab20-06dad3568712', N'ca01f704-b5fc-49d0-8370-a799f44fe28f')
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'c9435f13-0a9e-4767-a35b-7eb602cf6e08', N'ca01f704-b5fc-49d0-8370-a799f44fe28f')
INSERT [dbo].[account_role] ([account_id], [role_id]) VALUES (N'9808e628-3fa1-4f2b-87ca-78972c5ce0bd', N'ca01f704-b5fc-49d0-8370-a799f44fe28f')
GO
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'0014f66b-5e66-48ca-be94-6c20276eccfe', CAST(N'2024-04-05T08:50:00.0000000' AS DateTime2), N'harper.white1@example.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', N'', CAST(N'2024-11-28T22:40:53.1414210' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'029c8725-9ab0-409d-8198-a393bdfafd20', CAST(N'2025-05-17T06:30:18.0320980' AS DateTime2), N'ggducvu6@gmail.com', N'$2a$10$BUaV7YsXtzFMsgsiyKdIfegkuW9egcKAEEkrHkJlGfIj.W3GjLnqW', N'', CAST(N'2025-05-17T06:30:18.0321560' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'06453093-9308-42ea-a9f2-0e91cc7a6109', CAST(N'2024-11-20T20:39:37.5341820' AS DateTime2), N'chrisharris@gmail.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', N'', CAST(N'2024-11-20T20:39:37.5341820' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'07d44ddd-76ac-4e83-8c6b-a33e6a865f93', CAST(N'2023-12-15T17:50:00.0000000' AS DateTime2), N'charlotte.gonzalez1@example.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', NULL, CAST(N'2023-12-15T18:30:00.0000000' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'0a093d20-4fae-47b2-8a27-65f1d51b0b48', CAST(N'2025-05-17T05:57:39.7691920' AS DateTime2), N'ggducvu2@gmail.com', N'$2a$10$C.XIv5Hu.u1R4eD8rc1m0uBltb0CPe9yGvya1.q0y6D1HeLjFGiu.', N'', CAST(N'2025-05-17T05:57:39.7693370' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'0a19b1f4-0ea5-41e5-9115-bec28fac6025', CAST(N'2023-02-10T14:10:00.0000000' AS DateTime2), N'jane.smith1@example.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', NULL, CAST(N'2023-02-10T15:00:00.0000000' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'0bc6778f-9808-4a92-a2e6-9179569b27cc', CAST(N'2023-04-20T09:45:00.0000000' AS DateTime2), N'emily.brown@example.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', NULL, CAST(N'2023-04-20T10:30:00.0000000' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'0be6c356-1749-43e3-9f06-9ad59d09312f', CAST(N'2023-08-18T08:30:00.0000000' AS DateTime2), N'james.davis@example.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', NULL, CAST(N'2023-08-18T09:10:00.0000000' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'0e9ed12d-f1a9-4a2c-b08e-c9184239407a', NULL, N'emilyj@gmail.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', NULL, CAST(N'2024-11-30T21:01:10.3070480' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'0ebe2399-3a12-4115-a57e-bc2468e3160d', CAST(N'2023-06-10T18:20:00.0000000' AS DateTime2), N'chris.taylor1@example.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', NULL, CAST(N'2023-06-10T19:00:00.0000000' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'10aa62c0-374d-49b4-907f-891aab9b0ed5', CAST(N'2023-09-25T14:05:00.0000000' AS DateTime2), N'isabella.garcia1@example.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', NULL, CAST(N'2023-09-25T14:45:00.0000000' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'13470069-458d-43de-af84-84c1ed593491', CAST(N'2024-11-20T20:28:15.6100850' AS DateTime2), N'sunday@gmail.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', N'', CAST(N'2024-11-20T20:28:15.6100850' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'25a6782f-3807-44a4-a3e8-8645c175e232', CAST(N'2024-11-20T20:27:21.5837760' AS DateTime2), N'tranthibich@gmail.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', N'', CAST(N'2024-11-20T20:27:21.5837760' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'2ce850c8-c235-4372-86f7-d7b43fa113e0', CAST(N'2023-07-15T12:50:00.0000000' AS DateTime2), N'sophia.miller1@example.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', NULL, CAST(N'2023-07-15T13:40:00.0000000' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'327d6667-c981-411b-b4ba-0aa5b07a1d0c', CAST(N'2024-11-20T20:38:49.8688840' AS DateTime2), N'thutram@gmail.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', N'', CAST(N'2024-11-20T20:38:49.8688840' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'3347ff71-9465-4b08-b9ae-b8c8ae6fae0a', CAST(N'2025-04-24T08:47:04.6389220' AS DateTime2), N'11112233333333456@gmail.com', N'$2a$10$FgnVpuRizVaPm5wgcHfXB.zk4h8P5Y6UQW2KHUyZSg8URafgF2QIO', N'', CAST(N'2025-04-24T08:47:04.6390100' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'35c5e35d-3f2f-48c3-90d1-7b35c24cbdf0', CAST(N'2024-11-20T20:35:50.4701530' AS DateTime2), N'minhduc@gmail.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', N'', CAST(N'2025-05-18T03:30:25.4226530' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'3674e400-9029-4fae-870a-ab3cb73b4231', NULL, N'jessicat@gmail.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', NULL, CAST(N'2024-11-27T22:11:05.9486600' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'42be33a1-5193-4601-b14b-d04f667bad42', CAST(N'2024-11-20T20:37:42.6106260' AS DateTime2), N'khanhquang@gmail.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', N'', CAST(N'2024-11-20T20:37:42.6106260' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'4313c04b-2c40-4239-b0e8-a149606a9467', CAST(N'2024-11-20T20:40:04.4502280' AS DateTime2), N'daniel@gmail.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', N'', CAST(N'2024-11-20T20:40:04.4502280' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'4595e079-0405-4e73-8c27-f334b3d29c00', CAST(N'2023-09-25T14:05:00.0000000' AS DateTime2), N'isabella.garcia@example.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', NULL, CAST(N'2023-09-25T14:45:00.0000000' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'473c80bc-2244-4a0e-b1b7-c6b260519bc8', CAST(N'2023-08-18T08:30:00.0000000' AS DateTime2), N'james.davis1@example.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', NULL, CAST(N'2023-08-18T09:10:00.0000000' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'4974d2ac-047e-4fcd-8f38-7ef7c5636f67', NULL, N'johnsmith@gmail.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', NULL, CAST(N'2024-11-27T22:12:25.8962420' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'4b9e2b88-69ba-4625-9cfb-a3ed864c2f61', CAST(N'2024-11-20T20:39:21.1671050' AS DateTime2), N'charlotte@gmail.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', N'', CAST(N'2024-11-20T20:39:21.1671050' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'4f33c122-c70e-4723-9fbd-c98c1a0e2210', CAST(N'2023-07-15T12:50:00.0000000' AS DateTime2), N'sophia.miller@example.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', NULL, CAST(N'2023-07-15T13:40:00.0000000' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'51db70a0-1b4a-4410-a062-356febfdaf0a', CAST(N'2025-03-22T19:05:05.7501870' AS DateTime2), N'111@gmail.com', N'$2a$10$ss3/ccMcsub63bicFG4Eluh0ndjX0W16dqg5Ak4cq0KJm84EwTwwC', N'', CAST(N'2025-03-22T19:05:05.7501870' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'5846a023-223a-4208-8dfc-dd909ee5dee7', CAST(N'2025-05-17T05:37:26.2103090' AS DateTime2), N'ggducvu1@gmail.com', N'$2a$10$uRPqMec6wUwhHrvVu78D2ut6N/ZMHIney4O47hhLNANoHEM.UBf5O', N'', CAST(N'2025-05-17T05:37:26.2103620' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'59d99668-ac46-4c9e-9a0f-641292f662d2', CAST(N'2025-05-17T06:13:36.7208920' AS DateTime2), N'ggducvu3@gmail.com', N'$2a$10$oZAQnl7Ln1FRzY62rlomwuT8uOw/0RbRPayjDGCIugmV33pWIqkZi', N'', CAST(N'2025-05-17T06:13:36.7209370' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'5be19781-d9ad-46d0-a916-a5588c84f15b', CAST(N'2023-04-20T09:45:00.0000000' AS DateTime2), N'emily.brown1@example.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', NULL, CAST(N'2023-04-20T10:30:00.0000000' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'5d40c71a-f775-41fc-8ef9-89f2613e1ee3', CAST(N'2024-01-05T09:35:00.0000000' AS DateTime2), N'benjamin.perez1@example.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', NULL, CAST(N'2024-01-05T10:15:00.0000000' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'5fe2f6f3-8f55-463f-a158-e13ba0c61b67', NULL, N'jameswilson@gmail.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', NULL, CAST(N'2024-11-27T22:02:54.9736980' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'66858ecc-2a49-4771-9391-6b3799d9810e', CAST(N'2024-11-20T20:27:33.8010710' AS DateTime2), N'leminhhoang@gmail.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', N'', CAST(N'2024-11-20T20:27:33.8010710' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'69ae1c22-01b5-4947-a090-5b7750d0682f', CAST(N'2024-11-27T21:51:16.9880020' AS DateTime2), N'ad1223123312@gmail.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', N'', CAST(N'2024-11-27T21:51:16.9880020' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'69fd74ee-118e-44a7-bfe2-6cb52d35f25e', CAST(N'2024-11-20T20:36:25.4732580' AS DateTime2), N'thanhtam@gmail.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', N'', CAST(N'2024-11-20T20:36:25.4732580' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'709c8dca-b2d9-4c8f-b87c-a324ebf6f626', CAST(N'2025-04-24T08:39:02.4112910' AS DateTime2), N'sdds32156465456s@gmail.com', N'$2a$10$ga2mBiyV5FAn6Zf6YdX/1e4RHav0RmpMOy7GCtr4rg2rHutbWW./S', N'', CAST(N'2025-04-24T08:39:02.4113460' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'71156581-0fb9-4072-9cad-6ef4dd4cd21a', CAST(N'2024-12-01T14:26:23.1660900' AS DateTime2), N'ggducvu@gmail.com', N'$2a$10$QcGbIPXqoSEdxcCRk9ip5.2MjYklF8VOIINzeLNTBlBXr3gWA/nDS', N'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnZ2R1Y3Z1QGdtYWlsLmNvbSIsImV4cCI6MTc1NjE3OTAzMiwiaWF0IjoxNzQ3NTM5MDMyLCJ1c2VyIjp7ImlkIjoiNzExNTY1ODEtMGZiOS00MDcyLTljYWQtNmVmNGRkNGNkMjFhIiwiZW1haWwiOiJnZ2R1Y3Z1QGdtYWlsLmNvbSJ9fQ.0xjbf0ZTcYNkZALlfDjbymVxUoYsOoin8yofH1RyTUlKpGZzmJ1ZrDhbufpO4A9Z-Sq9vneJd29TBsva4GdgrA', CAST(N'2025-05-18T03:30:32.8903020' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'75094cd0-fb24-49e5-b35e-9b9fdde9b685', CAST(N'2024-07-25T10:40:00.0000000' AS DateTime2), N'evelyn.lewis1@example.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', NULL, CAST(N'2024-07-25T11:20:00.0000000' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'7afc4e87-d4eb-4c58-a901-9ad9e920367e', CAST(N'2025-05-17T06:22:34.9438010' AS DateTime2), N'ggducvu12@gmail.com', N'$2a$10$bcZAv5O.2b0wMMZkv8MeRec6CqjCc29xTfbebnWN8r7m9md8/A1xG', N'', CAST(N'2025-05-17T06:22:34.9438540' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'7beb1cad-3134-4a30-9c27-377941b3f788', CAST(N'2024-06-15T13:30:00.0000000' AS DateTime2), N'henry.clark1@example.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', NULL, CAST(N'2024-06-15T14:10:00.0000000' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'84f37ab0-feb4-4336-908a-48d52cfae4c5', CAST(N'2025-04-24T08:55:05.0253970' AS DateTime2), N'2@gmail.com', N'$2a$10$ykJJuIlRFBqYc9cNCk5PHO/py1LvzSy3fqS5zInKSCKPvfL7BHYde', N'', CAST(N'2025-04-24T08:55:05.0254700' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'88cc569a-b129-4d10-854d-206d22ba25c1', CAST(N'2024-03-15T12:10:00.0000000' AS DateTime2), N'lucas.thompson1@example.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', NULL, CAST(N'2024-03-15T12:50:00.0000000' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'899ef0e7-b1db-4400-9071-1aad06ad58c0', CAST(N'2024-11-20T20:40:32.0404650' AS DateTime2), N'grace@gmail.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', N'', CAST(N'2024-11-20T20:40:32.0404650' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'8b30f4ac-5637-4a7a-994c-56205866ec10', CAST(N'2024-11-20T20:28:50.7330540' AS DateTime2), N'michaelbrown@gmail.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', N'', CAST(N'2024-11-20T20:28:50.7330540' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'929e39a1-17a4-4cbd-a361-76aaf2325def', CAST(N'2024-05-10T11:20:00.0000000' AS DateTime2), N'amelia.harris1@example.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', NULL, CAST(N'2024-05-10T12:00:00.0000000' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'95070376-aa41-474a-ac1f-d10d7463d030', CAST(N'2024-11-27T22:59:46.7424250' AS DateTime2), N'thanhpham@gmail.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', N'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0aGFuaHBoYW1AZ21haWwuY29tIiwiZXhwIjoxNzU2MTg1NTQ5LCJpYXQiOjE3NDc1NDU1NDksInVzZXIiOnsiaWQiOiI5NTA3MDM3Ni1hYTQxLTQ3NGEtYWMxZi1kMTBkNzQ2M2QwMzAiLCJlbWFpbCI6InRoYW5ocGhhbUBnbWFpbC5jb20ifX0.jdlP1a0KVl2ZLJzkPpXpARezXnaQIFntsI9tMoPQvl6Jx6Zlsea5KIpUb_ArhCbUTtY1BNbi56mCjjE5D6l7PQ', CAST(N'2025-05-18T05:19:10.0117040' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'9808e628-3fa1-4f2b-87ca-78972c5ce0bd', CAST(N'2025-04-25T06:45:33.4552680' AS DateTime2), N'123123132@gmail.com', N'$2a$10$VTGyji9Y/O7TrXmiRIHj8OcH/REa6j69AS9D3N1MPOab9heuMwONW', N'', CAST(N'2025-04-25T06:45:33.4553070' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'98b9b703-dbb2-43f8-96d0-3daa3e91a45f', CAST(N'2024-02-20T14:45:00.0000000' AS DateTime2), N'mia.hernandez1@example.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', NULL, CAST(N'2024-02-20T15:25:00.0000000' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'a1c6430b-00d6-4147-bedc-cc61bdb6f4f4', CAST(N'2023-01-15T08:30:00.0000000' AS DateTime2), N'john.doe@example.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', NULL, CAST(N'2023-01-15T09:00:00.0000000' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'a5b9fdcb-1999-4862-90a0-2a449b22d9da', CAST(N'2024-11-20T20:26:20.1789440' AS DateTime2), N'quang@gmail.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', N'', CAST(N'2024-12-01T14:26:10.7089820' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'a64c105a-1c0b-4653-8897-2d5e11d2f577', CAST(N'2023-10-10T10:20:00.0000000' AS DateTime2), N'oliver.martinez@example.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', NULL, CAST(N'2023-10-10T11:00:00.0000000' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'a901eb86-f5ad-4702-ab20-06dad3568712', CAST(N'2025-05-17T06:40:36.3683530' AS DateTime2), N'ggducvu31@gmail.com', N'$2a$10$dt8CicXlJIXGp/rtKKhHAeMDKWtvGKYnZuyGQ0FMjlgu4LNXUyg7m', N'', CAST(N'2025-05-17T06:40:36.3684080' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'b3da5aba-545f-4c17-937c-f7a8b01200b4', CAST(N'2024-11-20T20:26:05.5899340' AS DateTime2), N'thanh@gmail.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', N'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0aGFuaEBnbWFpbC5jb20iLCJleHAiOjE3NDE0NDY5MjUsImlhdCI6MTczMjgwNjkyNSwidXNlciI6eyJpZCI6ImIzZGE1YWJhLTU0NWYtNGMxNy05MzdjLWY3YThiMDEyMDBiNCIsImVtYWlsIjoidGhhbmhAZ21haWwuY29tIn19.jkQ3DxnQD_Y2p_HbkRGmVT3HQHeDah9fS5fhMhePpH_m8gXcEfz3P5VStxNDbmk3485Xz6ckX_N_-i6sI0iRUg', CAST(N'2024-11-28T22:15:25.1440810' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'b70c29da-7ea1-4d47-9d19-d83e2028c5ca', CAST(N'2024-11-27T21:49:59.9009380' AS DateTime2), N'ads@gmail.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', N'', CAST(N'2024-11-30T20:33:06.3741180' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'bfb16e43-3f90-44b1-b262-edf1f97c0a8d', CAST(N'2023-03-15T11:25:00.0000000' AS DateTime2), N'mike.johnson@example.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', NULL, CAST(N'2023-03-15T12:30:00.0000000' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'c399aa2b-b5e2-4c48-8850-eb94f2ff09dc', CAST(N'2023-05-25T16:35:00.0000000' AS DateTime2), N'david.wilson@example.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', NULL, CAST(N'2023-05-25T17:15:00.0000000' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'c9435f13-0a9e-4767-a35b-7eb602cf6e08', CAST(N'2025-04-24T08:28:44.7091880' AS DateTime2), N'adadaaddaadadaddadd@gmail.com', N'$2a$10$ohswVuHxhX2FPCIThLKC8uFC8csU3DdhSC19Wt4Zy5gvST0fGZhpO', N'', CAST(N'2025-04-24T08:28:44.7092250' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'c9e489e5-4882-4e8f-bab5-c3cef47e104d', CAST(N'2023-01-15T08:30:00.0000000' AS DateTime2), N'john.doe1@example.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', NULL, CAST(N'2023-01-15T09:00:00.0000000' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'cb2e5d3e-3640-4c6b-b058-10c65322b2f0', CAST(N'2023-05-25T16:35:00.0000000' AS DateTime2), N'david.wilson1@example.com', N'$2a$10$Y/M8Us69vtTvbYgnLLrS6uXWoObRU2pfjCztZJ.9aL4iWjWG0K0ju', NULL, CAST(N'2023-05-25T17:15:00.0000000' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'cf890bc9-55eb-4adc-915a-40aa62fa2cc3', CAST(N'2024-11-20T20:26:36.5175240' AS DateTime2), N'nguyenvana@gmail.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', NULL, CAST(N'2024-11-20T20:26:36.5175240' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'd02fab0b-ffb5-4da4-bb5e-1ac8f48e4eee', CAST(N'2024-11-20T20:37:19.5858360' AS DateTime2), N'vanthanh@gmail.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', N'', CAST(N'2025-03-21T11:07:20.7099500' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'd7f0f62f-ec5c-4511-a6f1-34e7974283da', CAST(N'2024-11-20T20:39:12.2548060' AS DateTime2), N'benjaminm@gmail.com', N'$2a$10$6kWzGUT99XEqRK/YrUopnO.DUQzT.ef1gFogmx/kP3RyjClDkl1e2', NULL, CAST(N'2024-11-20T20:39:12.2548060' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'd8a85423-b22b-4feb-b3f8-2abb77539199', CAST(N'2023-10-10T10:20:00.0000000' AS DateTime2), N'oliver.martinez1@example.com', N'$2a$10$Y/M8Us69vtTvbYgnLLrS6uXWoObRU2pfjCztZJ.9aL4iWjWG0K0ju', NULL, CAST(N'2023-10-10T11:00:00.0000000' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'dd123f04-4bf3-4c1e-9ade-a1c4d8f5241b', CAST(N'2023-02-10T14:10:00.0000000' AS DateTime2), N'jane.smith@example.com', N'$2a$10$Y/M8Us69vtTvbYgnLLrS6uXWoObRU2pfjCztZJ.9aL4iWjWG0K0ju', NULL, CAST(N'2023-02-10T15:00:00.0000000' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'e80215f7-9d0f-4651-b8f1-6e68d5c4d43b', CAST(N'2025-04-24T08:28:24.9023480' AS DateTime2), N'12312313@gmail.com', N'$2a$10$FkmzOWrSCCNlgBU9IZEAgu/v37.zrzXOYMyw2uZ76h2xoS1kbOf92', N'', CAST(N'2025-04-24T08:28:24.9023960' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'ebaba9bb-1fba-4d94-b967-ac19ec1ff975', CAST(N'2023-03-15T11:25:00.0000000' AS DateTime2), N'mike.johnson1@example.com', N'$2a$10$Y/M8Us69vtTvbYgnLLrS6uXWoObRU2pfjCztZJ.9aL4iWjWG0K0ju', NULL, CAST(N'2023-03-15T12:30:00.0000000' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'ec6b93df-9b3a-4fa3-a41c-119ff5fd27b3', CAST(N'2024-11-20T20:29:09.2126470' AS DateTime2), N'sarahdavis@gmail.com', N'$2a$10$Y/M8Us69vtTvbYgnLLrS6uXWoObRU2pfjCztZJ.9aL4iWjWG0K0ju', N'', CAST(N'2024-11-20T20:29:09.2126470' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'f26861e4-5cab-46ce-a0d8-006ba1f13876', NULL, N'ggduck@gmail.com', N'$2a$10$kpPdd8RvORSblz.3SUlLCuFssjlfiqPLz1ARcb.hy02n96QSxL/PO', N'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnZ2R1Y2tAZ21haWwuY29tIiwiZXhwIjoxNzUxMTcwMDcyLCJpYXQiOjE3NDI1MzAwNzIsInVzZXIiOnsiaWQiOiJmMjY4NjFlNC01Y2FiLTQ2Y2UtYTBkOC0wMDZiYTFmMTM4NzYiLCJlbWFpbCI6ImdnZHVja0BnbWFpbC5jb20ifX0.QVnkfoxMsKYRmwLVy0DGSBYcWLR7kYf35raSknPPYPowjMHWMzTsr_74N6evmeZb-ZjY-JqianecKAUaLBZSJQ', CAST(N'2025-03-21T11:07:52.0465530' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'f29408f0-e777-49bc-98fa-c431fe3fd941', CAST(N'2024-08-18T15:00:00.0000000' AS DateTime2), N'jack.robinson1@example.com', N'$2a$10$Y/M8Us69vtTvbYgnLLrS6uXWoObRU2pfjCztZJ.9aL4iWjWG0K0ju', NULL, CAST(N'2024-08-18T15:45:00.0000000' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'f2d0eb38-cdb0-4177-a359-cfc839068b26', CAST(N'2025-04-24T08:28:16.6237690' AS DateTime2), N'111111111@gmail.com', N'$2a$10$HH4X096BNmkiCn4XQQwoyOGLNRDlJPW9TyhatAW7JbkSUplLVjaQu', N'', CAST(N'2025-04-24T08:28:16.6238280' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'f89f25e8-1cbc-4dcb-81b9-1c7e4e305a0c', CAST(N'2023-11-20T15:30:00.0000000' AS DateTime2), N'alex.lee1@example.com', N'$2a$10$Y/M8Us69vtTvbYgnLLrS6uXWoObRU2pfjCztZJ.9aL4iWjWG0K0ju', NULL, CAST(N'2023-11-20T16:15:00.0000000' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'f94f1160-de09-4f7d-a527-da5d7117079b', CAST(N'2025-04-24T06:39:03.8627350' AS DateTime2), N'111111@gmail.com', N'$2a$10$.soPtGxJqK9xPh4TbKNjCOU9xcd1lWLfRnmtCvVa4eRtHpJtE8M2G', N'', CAST(N'2025-04-24T06:39:03.8628470' AS DateTime2))
INSERT [dbo].[accounts] ([id], [created_at], [email], [password], [refresh_token], [updated_at]) VALUES (N'fad7775d-441f-4d82-854b-aa17064267ad', CAST(N'2024-11-20T20:26:11.7264230' AS DateTime2), N'kha@gmail.com', N'$2a$10$Y/M8Us69vtTvbYgnLLrS6uXWoObRU2pfjCztZJ.9aL4iWjWG0K0ju', N'', CAST(N'2024-11-20T20:26:11.7264230' AS DateTime2))
GO
INSERT [dbo].[blacklisted_tokens] ([id], [expiry_date], [token]) VALUES (N'0f8b389b-9d08-4768-bccc-5f0f9e630c4b', CAST(N'2025-04-24T09:36:43.7448100+00:00' AS DateTimeOffset), N'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtaW5oZHVjQGdtYWlsLmNvbSIsInBlcm1pc3Npb24iOlsiUk9MRV9VU0VSX0NSRUFURSIsIlJPTEVfVVNFUl9VUERBVEUiXSwiZXhwIjoxNzU0MTI2MjgwLCJpYXQiOjE3NDU0ODYyODAsInVzZXIiOnsiaWQiOiIzNWM1ZTM1ZC0zZjJmLTQ4YzMtOTBkMS03YjM1YzI0Y2JkZjAiLCJlbWFpbCI6Im1pbmhkdWNAZ21haWwuY29tIn19.p5HgoKN4swn2wb-hN_Sqo1pNpdPaThSsV3ZUBv9JLbSo0gxZ6VVuCts6neJBwK1vrU9RzIj7JLxX3sVT44msSQ')
INSERT [dbo].[blacklisted_tokens] ([id], [expiry_date], [token]) VALUES (N'51a04a43-eb9f-4449-939f-f11c668f7246', CAST(N'2025-04-24T15:42:13.0134340+00:00' AS DateTimeOffset), N'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtaW5oZHVjQGdtYWlsLmNvbSIsInBlcm1pc3Npb24iOlsiUk9MRV9VU0VSX0NSRUFURSIsIlJPTEVfVVNFUl9VUERBVEUiXSwiZXhwIjoxNzU0MTQ3NjA3LCJpYXQiOjE3NDU1MDc2MDcsInVzZXIiOnsiaWQiOiIzNWM1ZTM1ZC0zZjJmLTQ4YzMtOTBkMS03YjM1YzI0Y2JkZjAiLCJlbWFpbCI6Im1pbmhkdWNAZ21haWwuY29tIn19.x5UYBT8HxaJidod1nX7EqVwXzYwVOzOpuqgkU6zYj9pwB_AtjDadpNE2fbMIRrmvyLDnMEY2AAEtL6vSfi_tqA')
INSERT [dbo].[blacklisted_tokens] ([id], [expiry_date], [token]) VALUES (N'71cca79c-948f-4ec4-8875-bc6a797354b0', CAST(N'2025-04-24T14:52:26.0950120+00:00' AS DateTimeOffset), N'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtaW5oZHVjQGdtYWlsLmNvbSIsInBlcm1pc3Npb24iOlsiUk9MRV9VU0VSX0NSRUFURSIsIlJPTEVfVVNFUl9VUERBVEUiXSwiZXhwIjoxNzU0MTQ0MDkwLCJpYXQiOjE3NDU1MDQwOTAsInVzZXIiOnsiaWQiOiIzNWM1ZTM1ZC0zZjJmLTQ4YzMtOTBkMS03YjM1YzI0Y2JkZjAiLCJlbWFpbCI6Im1pbmhkdWNAZ21haWwuY29tIn19.f5Yop44mCBSDtrLaMZxEemc8RUbkYVg95gLRqhprc7onw6CXsMhZCn2lrEe13-E1eorc2-h715avG5QPldOkGQ')
INSERT [dbo].[blacklisted_tokens] ([id], [expiry_date], [token]) VALUES (N'8142aceb-82d8-4038-b341-1076b35a7b66', CAST(N'2025-05-18T03:30:25.3689760+00:00' AS DateTimeOffset), N'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtaW5oZHVjQGdtYWlsLmNvbSIsInBlcm1pc3Npb24iOlsiUk9MRV9VU0VSX0NSRUFURSIsIlJPTEVfVVNFUl9VUERBVEUiXSwiZXhwIjoxNzU2MTc4OTk3LCJpYXQiOjE3NDc1Mzg5OTcsInVzZXIiOnsiaWQiOiIzNWM1ZTM1ZC0zZjJmLTQ4YzMtOTBkMS03YjM1YzI0Y2JkZjAiLCJlbWFpbCI6Im1pbmhkdWNAZ21haWwuY29tIn19.egJ0tjjaPJtpc_EHPxsZfm6-9QZL8PA_GFmY9tG3YnGsiQMKxXHM9o055MWuqezdAerVNSvw1DgtcIaqXAbU2w')
INSERT [dbo].[blacklisted_tokens] ([id], [expiry_date], [token]) VALUES (N'86c2cee8-6045-44de-88d6-813e223f0d85', CAST(N'2025-04-24T15:54:17.6038080+00:00' AS DateTimeOffset), N'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtaW5oZHVjQGdtYWlsLmNvbSIsInBlcm1pc3Npb24iOlsiUk9MRV9VU0VSX0NSRUFURSIsIlJPTEVfVVNFUl9VUERBVEUiXSwiZXhwIjoxNzU0MTQ5MzM3LCJpYXQiOjE3NDU1MDkzMzcsInVzZXIiOnsiaWQiOiIzNWM1ZTM1ZC0zZjJmLTQ4YzMtOTBkMS03YjM1YzI0Y2JkZjAiLCJlbWFpbCI6Im1pbmhkdWNAZ21haWwuY29tIn19.SMmjTuhFk9F6A0aRpgLqJahBoh9ifgBRZe5aJ0j75tMZV7YTBmiBCxT_ftgEhcqDKQMBsKwrSAbuIylrN26TBg')
INSERT [dbo].[blacklisted_tokens] ([id], [expiry_date], [token]) VALUES (N'a2e15f8a-57ec-4d56-8af7-4a1d9e46cd96', CAST(N'2025-04-19T03:38:58.8157790+00:00' AS DateTimeOffset), N'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtaW5oZHVjQGdtYWlsLmNvbSIsInBlcm1pc3Npb24iOlsiUk9MRV9VU0VSX0NSRUFURSIsIlJPTEVfVVNFUl9VUERBVEUiXSwiZXhwIjoxNzQ1MDM0Mjk3LCJpYXQiOjE3NDUwMzM5MzcsInVzZXIiOnsiaWQiOiIzNWM1ZTM1ZC0zZjJmLTQ4YzMtOTBkMS03YjM1YzI0Y2JkZjAiLCJlbWFpbCI6Im1pbmhkdWNAZ21haWwuY29tIn19.aAHBshnXWHlo6jTm37z2Pysf7o9-ilDRjmDcIB5zpsSg4y1IubfhrRe2zCynan9WV2kpJOiBa77beRLID_DfVA')
INSERT [dbo].[blacklisted_tokens] ([id], [expiry_date], [token]) VALUES (N'a7d4f645-f0fa-4295-973c-4f6ed9eee601', CAST(N'2025-04-24T10:32:13.8405570+00:00' AS DateTimeOffset), N'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtaW5oZHVjQGdtYWlsLmNvbSIsInBlcm1pc3Npb24iOlsiUk9MRV9VU0VSX0NSRUFURSIsIlJPTEVfVVNFUl9VUERBVEUiXSwiZXhwIjoxNzU0MTMwMDU4LCJpYXQiOjE3NDU0OTAwNTgsInVzZXIiOnsiaWQiOiIzNWM1ZTM1ZC0zZjJmLTQ4YzMtOTBkMS03YjM1YzI0Y2JkZjAiLCJlbWFpbCI6Im1pbmhkdWNAZ21haWwuY29tIn19.SP8dxWps6CsDTvNeHTGChltQReUZAuiGeumYaFoODc3kFAMn7yYZsHjpNO1fPaWs12SPoaX5mYQwuUsEilHqOw')
INSERT [dbo].[blacklisted_tokens] ([id], [expiry_date], [token]) VALUES (N'ba6118d2-dc93-403f-bd69-24307a4227ec', CAST(N'2025-04-24T09:59:03.7245000+00:00' AS DateTimeOffset), N'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtaW5oZHVjQGdtYWlsLmNvbSIsInBlcm1pc3Npb24iOlsiUk9MRV9VU0VSX0NSRUFURSIsIlJPTEVfVVNFUl9VUERBVEUiXSwiZXhwIjoxNzU0MTI3NDA4LCJpYXQiOjE3NDU0ODc0MDgsInVzZXIiOnsiaWQiOiIzNWM1ZTM1ZC0zZjJmLTQ4YzMtOTBkMS03YjM1YzI0Y2JkZjAiLCJlbWFpbCI6Im1pbmhkdWNAZ21haWwuY29tIn19.jCQqaJXy0bYwHJvYKI5X6utRDWdAPmXiEi9AZGT_9q8QQGYqpfoEwIP5edkGPtn_9WVRSIR61S_zVTJPDGgPMA')
INSERT [dbo].[blacklisted_tokens] ([id], [expiry_date], [token]) VALUES (N'bbfdbc3b-a433-4ba8-b542-a2934feb7cfc', CAST(N'2025-04-24T15:13:23.6096880+00:00' AS DateTimeOffset), N'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtaW5oZHVjQGdtYWlsLmNvbSIsInBlcm1pc3Npb24iOlsiUk9MRV9VU0VSX0NSRUFURSIsIlJPTEVfVVNFUl9VUERBVEUiXSwiZXhwIjoxNzU0MTQ3MDA4LCJpYXQiOjE3NDU1MDcwMDgsInVzZXIiOnsiaWQiOiIzNWM1ZTM1ZC0zZjJmLTQ4YzMtOTBkMS03YjM1YzI0Y2JkZjAiLCJlbWFpbCI6Im1pbmhkdWNAZ21haWwuY29tIn19.dt64pYz87cN7W-GHg_MKfx40JWPRvFaPBAWpfqagFR44vdld63aIcLi9W_3HKcWJCJOi7s44IQSF-VOeaJFMAg')
INSERT [dbo].[blacklisted_tokens] ([id], [expiry_date], [token]) VALUES (N'c1296bcb-8eb9-4e8b-8107-10621bbbf36a', CAST(N'2025-04-24T10:58:15.8603950+00:00' AS DateTimeOffset), N'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtaW5oZHVjQGdtYWlsLmNvbSIsInBlcm1pc3Npb24iOlsiUk9MRV9VU0VSX0NSRUFURSIsIlJPTEVfVVNFUl9VUERBVEUiXSwiZXhwIjoxNzU0MTMxNjEzLCJpYXQiOjE3NDU0OTE2MTMsInVzZXIiOnsiaWQiOiIzNWM1ZTM1ZC0zZjJmLTQ4YzMtOTBkMS03YjM1YzI0Y2JkZjAiLCJlbWFpbCI6Im1pbmhkdWNAZ21haWwuY29tIn19._t6dPpQqrI10u_YOQEVbLrMHSCi_w5RWMMIDvOF-ExirIlMtO2YHpFepDTISUXrDf7D-eG9somaI1QgkiJih9w')
INSERT [dbo].[blacklisted_tokens] ([id], [expiry_date], [token]) VALUES (N'd7d1a489-cf6a-4647-b04b-55acfbc72187', CAST(N'2025-04-24T10:46:49.1019230+00:00' AS DateTimeOffset), N'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtaW5oZHVjQGdtYWlsLmNvbSIsInBlcm1pc3Npb24iOlsiUk9MRV9VU0VSX0NSRUFURSIsIlJPTEVfVVNFUl9VUERBVEUiXSwiZXhwIjoxNzU0MTMwNzM4LCJpYXQiOjE3NDU0OTA3MzgsInVzZXIiOnsiaWQiOiIzNWM1ZTM1ZC0zZjJmLTQ4YzMtOTBkMS03YjM1YzI0Y2JkZjAiLCJlbWFpbCI6Im1pbmhkdWNAZ21haWwuY29tIn19.uL6vLRzrSKhKsbylR0dNn5xkg6ghorrsH-H0y0rKWW0Q1BDBMWB14VINDvUtdIyDE39su4CTLw9PWU1EXKJ1uQ')
INSERT [dbo].[blacklisted_tokens] ([id], [expiry_date], [token]) VALUES (N'db71791d-1480-4b5b-bf78-b7afff658328', CAST(N'2025-04-24T10:20:50.7133600+00:00' AS DateTimeOffset), N'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtaW5oZHVjQGdtYWlsLmNvbSIsInBlcm1pc3Npb24iOlsiUk9MRV9VU0VSX0NSRUFURSIsIlJPTEVfVVNFUl9VUERBVEUiXSwiZXhwIjoxNzU0MTI5Nzk3LCJpYXQiOjE3NDU0ODk3OTcsInVzZXIiOnsiaWQiOiIzNWM1ZTM1ZC0zZjJmLTQ4YzMtOTBkMS03YjM1YzI0Y2JkZjAiLCJlbWFpbCI6Im1pbmhkdWNAZ21haWwuY29tIn19.jgIzIfdjuNcWYxCoqRymz_RYwFbf2SaQOOli3Cu428qGNXXttsPYb4qDf78clW57xIxK-Hz2tWIsHMZXKZnqgg')
INSERT [dbo].[blacklisted_tokens] ([id], [expiry_date], [token]) VALUES (N'dd044979-db36-46ff-bac5-3722faf23c22', CAST(N'2025-04-12T05:50:21.5676790+00:00' AS DateTimeOffset), N'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtaW5oZHVjQGdtYWlsLmNvbSIsInBlcm1pc3Npb24iOlsiUk9MRV9VU0VSX0NSRUFURSIsIlJPTEVfVVNFUl9VUERBVEUiXSwiZXhwIjoxNzQ0NDM3MzU0LCJpYXQiOjE3NDQ0MzY5OTQsInVzZXIiOnsiaWQiOiIzNWM1ZTM1ZC0zZjJmLTQ4YzMtOTBkMS03YjM1YzI0Y2JkZjAiLCJlbWFpbCI6Im1pbmhkdWNAZ21haWwuY29tIn19.l-6z-1sU_HsQf9rXjvOEnl4CY1hb-RistvhRxa9CA4YE_HdUhkowSX8zWdsHcb7Jm_RtuK1YNfq0AYQNwAan2Q')
INSERT [dbo].[blacklisted_tokens] ([id], [expiry_date], [token]) VALUES (N'f1d80e66-07ce-4dfd-8499-c1da960f27a7', CAST(N'2025-04-24T15:03:24.1354120+00:00' AS DateTimeOffset), N'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtaW5oZHVjQGdtYWlsLmNvbSIsInBlcm1pc3Npb24iOlsiUk9MRV9VU0VSX0NSRUFURSIsIlJPTEVfVVNFUl9VUERBVEUiXSwiZXhwIjoxNzU0MTQ2MzUzLCJpYXQiOjE3NDU1MDYzNTMsInVzZXIiOnsiaWQiOiIzNWM1ZTM1ZC0zZjJmLTQ4YzMtOTBkMS03YjM1YzI0Y2JkZjAiLCJlbWFpbCI6Im1pbmhkdWNAZ21haWwuY29tIn19.iWfLZdCj2bGmbspRrwOuAJPGSa3wZdSweeSTY2XgOedjqSaJcz4FXDMZDzJDjEmmv8o0MgaXy1fSJBR6dXjmSg')
INSERT [dbo].[blacklisted_tokens] ([id], [expiry_date], [token]) VALUES (N'fa9441a1-5e70-4a40-8b04-3a58ec57e5d3', CAST(N'2025-04-17T09:49:16.9847830+00:00' AS DateTimeOffset), N'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtaW5oZHVjQGdtYWlsLmNvbSIsInBlcm1pc3Npb24iOlsiUk9MRV9VU0VSX0NSRUFURSIsIlJPTEVfVVNFUl9VUERBVEUiXSwiZXhwIjoxNzQ0ODgzNzA1LCJpYXQiOjE3NDQ4ODMzNDUsInVzZXIiOnsiaWQiOiIzNWM1ZTM1ZC0zZjJmLTQ4YzMtOTBkMS03YjM1YzI0Y2JkZjAiLCJlbWFpbCI6Im1pbmhkdWNAZ21haWwuY29tIn19.ypQNr86KydiMcWX5_ryDAdZwtqXnzxrle_lA7wxCXXucWbGfk2lHww1OLqgK37-p5BOQrEVJ4sgDgajSv3Wiiw')
GO
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'008b425f-fcbe-4b3a-952c-555835a4dc2c', N'undraw_profile.svg', CAST(N'2023-01-15T08:30:00.0000000' AS DateTime2), CAST(N'1992-03-25' AS Date), N'MALE', N'John Doe', N'111-222-3333', CAST(N'2023-01-15T09:00:00.0000000' AS DateTime2), N'ACTIVE', N'95070376-aa41-474a-ac1f-d10d7463d030')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'03b95c81-3f2f-4885-8506-2cdb991409a0', N'undraw_profile.svg', CAST(N'2023-07-15T12:50:00.0000000' AS DateTime2), CAST(N'1995-04-04' AS Date), N'FEMALE', N'Sophia Miller', N'777-888-9999', CAST(N'2023-07-15T13:40:00.0000000' AS DateTime2), N'INACTIVE', N'4f33c122-c70e-4723-9fbd-c98c1a0e2210')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'09f4d121-6ff3-458a-b3cf-394cfd169f2c', N'undraw_profile.svg', CAST(N'2023-09-25T14:05:00.0000000' AS DateTime2), CAST(N'1989-11-11' AS Date), N'FEMALE', N'Isabella Garcia', N'999-000-1111', CAST(N'2023-09-25T14:45:00.0000000' AS DateTime2), N'ACTIVE', N'4595e079-0405-4e73-8c27-f334b3d29c00')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'0ce238b8-da93-4a92-893d-fd9334e8e299', N'undraw_profile.svg', CAST(N'2023-10-10T10:20:00.0000000' AS DateTime2), CAST(N'1994-08-08' AS Date), N'MALE', N'Oliver Martinez', N'000-111-2222', CAST(N'2023-10-10T11:00:00.0000000' AS DateTime2), N'INACTIVE', N'a64c105a-1c0b-4653-8897-2d5e11d2f577')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'1148fc24-d623-41c4-bd76-2a8652176023', N'1732719774940-3.png', NULL, CAST(N'2003-10-14' AS Date), N'MALE', N'James Wilson', N'qwqwe', CAST(N'2024-11-27T22:02:54.9802460' AS DateTime2), N'INACTIVE', N'5fe2f6f3-8f55-463f-a158-e13ba0c61b67')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'152dea04-f753-4121-8c6a-0de329a9bec8', N'undraw_profile.svg', CAST(N'2024-11-20T20:27:21.5951350' AS DateTime2), NULL, NULL, N'Tran Thi Bich', NULL, CAST(N'2024-11-20T20:27:21.5951350' AS DateTime2), N'ACTIVE', N'25a6782f-3807-44a4-a3e8-8645c175e232')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'1baac045-077b-4291-824c-75b2c024aa7b', N'undraw_profile.svg', CAST(N'2024-11-20T20:28:50.7410540' AS DateTime2), NULL, NULL, N'Michael Brown', NULL, CAST(N'2024-11-20T20:28:50.7410540' AS DateTime2), N'ACTIVE', N'8b30f4ac-5637-4a7a-994c-56205866ec10')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'27e89974-70e5-4407-8a97-6bc2152bd5f5', NULL, CAST(N'2025-03-22T19:05:05.7912540' AS DateTime2), NULL, NULL, N'test1', NULL, CAST(N'2025-03-22T19:05:05.7912540' AS DateTime2), N'ACTIVE', N'51db70a0-1b4a-4410-a062-356febfdaf0a')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'2a965310-71c4-4d25-a257-257edd815e3a', N'1733037238933-undraw_profile_3.svg', NULL, CAST(N'1203-04-14' AS Date), NULL, N'quang', N'0369160539', CAST(N'2024-12-01T14:13:58.9917040' AS DateTime2), N'ACTIVE', N'a5b9fdcb-1999-4862-90a0-2a449b22d9da')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'2ba51a4f-4b25-4aaa-af82-16e7fbb06caf', NULL, CAST(N'2025-04-24T08:28:24.9311460' AS DateTime2), NULL, NULL, N'test1', NULL, CAST(N'2025-04-24T08:28:24.9311830' AS DateTime2), N'ACTIVE', N'e80215f7-9d0f-4651-b8f1-6e68d5c4d43b')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'4211e508-3dcd-4dd9-ae86-6a316c538cde', N'undraw_profile.svg', CAST(N'2024-11-20T20:27:33.8130740' AS DateTime2), NULL, NULL, N'Le Minh Hoang', NULL, CAST(N'2024-11-20T20:27:33.8130740' AS DateTime2), N'ACTIVE', N'66858ecc-2a49-4771-9391-6b3799d9810e')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'4294f714-2904-4bc3-80c4-4a22a2c295da', NULL, CAST(N'2025-05-17T06:22:35.0401970' AS DateTime2), NULL, NULL, N'1234', NULL, CAST(N'2025-05-17T06:22:35.0402250' AS DateTime2), N'ACTIVE', N'7afc4e87-d4eb-4c58-a901-9ad9e920367e')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'43624177-3caf-4ac0-98ff-c92b5bd0b6d5', NULL, CAST(N'2025-04-24T08:28:44.7283850' AS DateTime2), NULL, NULL, N'test1', NULL, CAST(N'2025-04-24T08:28:44.7284110' AS DateTime2), N'ACTIVE', N'c9435f13-0a9e-4767-a35b-7eb602cf6e08')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'480d6521-34fe-449c-abbb-9b2de9c4bca4', NULL, CAST(N'2025-05-17T06:13:36.8035880' AS DateTime2), NULL, NULL, N'2145', NULL, CAST(N'2025-05-17T06:13:36.8036320' AS DateTime2), N'ACTIVE', N'59d99668-ac46-4c9e-9a0f-641292f662d2')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'4c00511e-85e0-4121-a503-e2a4f5c2e7e9', N'undraw_profile.svg', CAST(N'2024-11-20T20:26:11.7344190' AS DateTime2), NULL, NULL, N'kha', NULL, CAST(N'2024-11-20T20:26:11.7344190' AS DateTime2), N'ACTIVE', N'fad7775d-441f-4d82-854b-aa17064267ad')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'4d8ead64-bf27-4e5a-b406-c8445d0d0634', NULL, CAST(N'2025-04-24T08:39:02.4955290' AS DateTime2), NULL, NULL, N'test1', NULL, CAST(N'2025-04-24T08:39:02.4955600' AS DateTime2), N'ACTIVE', N'709c8dca-b2d9-4c8f-b87c-a324ebf6f626')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'510d542e-9c65-453a-b9d6-afc52e9b1b72', NULL, CAST(N'2025-05-17T05:57:39.9436530' AS DateTime2), NULL, NULL, N'gg1', NULL, CAST(N'2025-05-17T05:57:39.9436850' AS DateTime2), N'ACTIVE', N'0a093d20-4fae-47b2-8a27-65f1d51b0b48')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'5dcb0fac-b785-43f3-a5b3-eca361b760ea', N'undraw_profile.svg', CAST(N'2024-11-20T20:26:05.5999320' AS DateTime2), NULL, NULL, N'thanh', NULL, CAST(N'2024-11-20T20:26:05.5999320' AS DateTime2), N'ACTIVE', N'b3da5aba-545f-4c17-937c-f7a8b01200b4')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'65d5b945-6b04-4609-9464-76923b1b0291', N'undraw_profile.svg', CAST(N'2023-04-20T09:45:00.0000000' AS DateTime2), CAST(N'1978-12-22' AS Date), N'FEMALE', N'Emily Brown', N'444-555-6666', CAST(N'2023-04-20T10:30:00.0000000' AS DateTime2), N'INACTIVE', N'0bc6778f-9808-4a92-a2e6-9179569b27cc')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'6922347f-a0a5-4be5-8c5d-030615bcda3f', N'1732720265897-dien-thoai-sony-xperia-10-vi-blue.png', NULL, CAST(N'2003-10-14' AS Date), N'MALE', N'Jessica Taylor', N'0923739836', CAST(N'2024-11-27T22:11:05.9536680' AS DateTime2), N'INACTIVE', N'3674e400-9029-4fae-870a-ab3cb73b4231')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'6984a48d-1b63-4bae-a6ea-a57bbe9e8a26', N'undraw_profile.svg', CAST(N'2024-11-20T20:28:15.6200810' AS DateTime2), NULL, NULL, N'sunday', NULL, CAST(N'2024-11-20T20:28:15.6200810' AS DateTime2), N'ACTIVE', N'13470069-458d-43de-af84-84c1ed593491')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'6dead9fc-778a-4f5e-927d-9d41a454dc38', N'undraw_profile.svg', CAST(N'2023-03-15T11:25:00.0000000' AS DateTime2), CAST(N'1990-10-12' AS Date), N'MALE', N'Mike Johnson', N'333-444-5555', CAST(N'2023-03-15T12:30:00.0000000' AS DateTime2), N'ACTIVE', N'bfb16e43-3f90-44b1-b262-edf1f97c0a8d')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'741341c2-2ce0-4efd-9be7-abc65bd0c4dd', N'undraw_profile.svg', CAST(N'2023-02-10T14:10:00.0000000' AS DateTime2), CAST(N'1985-07-18' AS Date), N'FEMALE', N'Jane Smith', N'222-333-4444', CAST(N'2023-02-10T15:00:00.0000000' AS DateTime2), N'INACTIVE', N'dd123f04-4bf3-4c1e-9ade-a1c4d8f5241b')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'759ecfb0-62af-460e-8014-21e073a198e5', N'undraw_profile.svg', CAST(N'2023-08-18T08:30:00.0000000' AS DateTime2), CAST(N'2001-06-30' AS Date), N'MALE', N'James Davis', N'888-999-0000', CAST(N'2023-08-18T09:10:00.0000000' AS DateTime2), N'ACTIVE', N'0be6c356-1749-43e3-9f06-9ad59d09312f')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'778f0ecc-7c75-4d67-bdf9-86995a6c41fa', N'undraw_profile.svg', CAST(N'2023-05-25T16:35:00.0000000' AS DateTime2), CAST(N'1983-05-15' AS Date), N'MALE', N'David Wilson', N'555-666-7777', CAST(N'2023-05-25T17:15:00.0000000' AS DateTime2), N'ACTIVE', N'c399aa2b-b5e2-4c48-8850-eb94f2ff09dc')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'77e12f7b-114b-451e-8385-ad2526607779', N'1732975270222-undraw_profile_3.svg', NULL, CAST(N'2003-10-14' AS Date), N'MALE', N'Emily Johnson', N'0147852369', CAST(N'2024-11-30T21:01:10.3165970' AS DateTime2), N'ACTIVE', N'0e9ed12d-f1a9-4a2c-b08e-c9184239407a')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'7cc877ae-f4b3-43e5-bea0-f770becdfdcd', NULL, CAST(N'2025-05-17T06:30:18.1357160' AS DateTime2), NULL, NULL, N'GG', NULL, CAST(N'2025-05-17T06:30:18.1357540' AS DateTime2), N'ACTIVE', N'029c8725-9ab0-409d-8198-a393bdfafd20')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'9a54db74-521e-4870-81aa-56925fceb6b0', N'1732975325408-1732975214118-1732717154530-1.png', NULL, CAST(N'2000-03-12' AS Date), NULL, N'GGDUck', N'0369852147', CAST(N'2024-11-30T21:02:05.4505900' AS DateTime2), N'ACTIVE', N'f26861e4-5cab-46ce-a0d8-006ba1f13876')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'9c347a01-d7d6-46b0-9e97-40f62e241518', NULL, CAST(N'2025-04-24T06:39:04.1529900' AS DateTime2), NULL, NULL, N'test1', NULL, CAST(N'2025-04-24T06:39:04.1530700' AS DateTime2), N'ACTIVE', N'f94f1160-de09-4f7d-a527-da5d7117079b')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'aa4bf708-b044-4759-9e40-ce454f0221ba', NULL, CAST(N'2025-05-17T05:37:26.2897710' AS DateTime2), NULL, NULL, N'4353', NULL, CAST(N'2025-05-17T05:37:26.2897910' AS DateTime2), N'ACTIVE', N'5846a023-223a-4208-8dfc-dd909ee5dee7')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'af0087a0-4a4e-4e97-8f3b-cf8785c81082', N'undraw_profile.svg', CAST(N'2024-11-20T20:29:09.2206490' AS DateTime2), NULL, NULL, N'Sarah Davis', NULL, CAST(N'2024-11-20T20:29:09.2206490' AS DateTime2), N'ACTIVE', N'ec6b93df-9b3a-4fa3-a41c-119ff5fd27b3')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'b1fe1298-de53-44ba-b436-22fc8b0d8bc0', NULL, CAST(N'2025-04-24T08:28:16.7022110' AS DateTime2), NULL, NULL, N'test1', NULL, CAST(N'2025-04-24T08:28:16.7022470' AS DateTime2), N'ACTIVE', N'f2d0eb38-cdb0-4177-a359-cfc839068b26')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'b69515b6-cb02-47d9-9715-e5d222b9ac38', N'undraw_profile.svg', CAST(N'2024-11-20T20:26:36.5265420' AS DateTime2), NULL, NULL, N'Nguyen Van A', NULL, CAST(N'2024-11-20T20:26:36.5265420' AS DateTime2), N'ACTIVE', N'cf890bc9-55eb-4adc-915a-40aa62fa2cc3')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'ba21e63f-c8cc-41c1-aa7c-bde769860887', NULL, CAST(N'2024-12-01T14:26:23.1850450' AS DateTime2), NULL, NULL, N'Minh Duc', NULL, CAST(N'2024-12-01T14:26:23.1850450' AS DateTime2), N'ACTIVE', N'71156581-0fb9-4072-9cad-6ef4dd4cd21a')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'c2862e14-03b9-4896-a05b-6359cd677106', NULL, CAST(N'2025-04-24T08:55:05.1073790' AS DateTime2), NULL, NULL, N'test1', NULL, CAST(N'2025-04-24T08:55:05.1074120' AS DateTime2), N'ACTIVE', N'84f37ab0-feb4-4336-908a-48d52cfae4c5')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'c5d62c29-7b16-4726-b5be-adfcf12d2275', NULL, CAST(N'2025-04-24T08:47:04.7280840' AS DateTime2), NULL, NULL, N'test1', NULL, CAST(N'2025-04-24T08:47:04.7281100' AS DateTime2), N'ACTIVE', N'3347ff71-9465-4b08-b9ae-b8c8ae6fae0a')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'e8950885-5ab2-4046-9a7c-f5acdf98c711', NULL, CAST(N'2025-05-17T06:40:36.4490160' AS DateTime2), NULL, NULL, N'123456', NULL, CAST(N'2025-05-17T06:40:36.4491000' AS DateTime2), N'ACTIVE', N'a901eb86-f5ad-4702-ab20-06dad3568712')
INSERT [dbo].[customers] ([id], [avatar], [created_at], [date_of_birth], [gender], [name], [phone], [updated_at], [user_status], [account_id]) VALUES (N'e908a20c-6c0e-4c49-847c-ff7f17da449a', N'1732720345867-1.png', NULL, CAST(N'2000-02-20' AS Date), N'MALE', N'John Smith', N'0123456789', CAST(N'2024-11-27T22:12:25.9001790' AS DateTime2), N'INACTIVE', N'4974d2ac-047e-4fcd-8f38-7ef7c5636f67')
GO
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'08624617-902A-4FEB-9D5F-EDBA9519485D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'0A144312-7A14-48AC-8179-3728685AAF4C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'0A96E625-2991-46A9-ADD2-4C7ACF876144')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'0B78E831-3752-4547-A0F7-C2CE2DD8891B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'0DDD389F-BAAF-42A7-80A1-9A3775A1C252')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'0F371E03-E1A9-4ED7-AB9D-7FEC4B6C585A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'0FC4465F-2F9E-4F64-B3A2-A3AB90B5D194')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'1004A870-B62E-4DD2-A54F-712B7552359D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'103B88A8-6EAA-4FB9-9DD1-B7D44C25DE25')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'14502C57-6B5A-4611-B08D-EBA91D34621A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'156CE29E-214C-42B8-A7CC-1768FDB27BBF')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'15FF5087-9B95-4BCC-943C-B5EA2F4E4FD8')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'16252B7D-1E01-4CD5-86EA-86397C660150')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'1A234409-3FA9-4765-A5B4-A3878433108F')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'1BE62AD0-C014-4A0F-8B6C-B3224C47509B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'1C3D609E-435A-480A-B427-573F0BBB0142')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'1E5F8877-31EA-4BDD-99DF-2B8F5CFFE7AF')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'1E5FC3B6-9D59-4E16-9018-9094C12EF05C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'1F6965F7-2F0F-4FCB-8D91-0C0ED7542520')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'2030CCB2-7CD8-4D63-ADEF-E226CF092A9A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'20A844BD-71AE-471A-AEC1-D3FC4D1CD12C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'217FBB33-6CF5-440A-A8B9-B3BFA0E86F72')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'2956124E-2B9B-47E2-A815-B75C0964A2BC')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'2F62EEA8-95FC-4655-A19D-D6FA10296514')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'3137A55F-7008-4D2A-816F-6758558523FC')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'32709C83-D5F8-437A-A208-4559646F7E42')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'32DD3EED-FDB4-40C9-AC32-B3AF639E4664')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'3363672E-0604-47D6-A7E6-9AB1BC139DAA')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'356850B1-762E-4FB6-B7E0-A07C8A28DD63')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'384420DC-4BC3-4531-977E-6644EF2EE86C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'39013242-78B2-4DD0-8F72-328CDCA88BA4')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'39C7F908-86B3-41B7-91D7-50004878473C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'3E8E7881-5B4C-44CB-914E-64F08B73A2CD')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'41F3A36A-3F76-48BE-8A1F-29B3386CA880')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'4635EDBE-7345-4DF5-969E-6300698DDE86')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'46402533-EE8F-4FD9-AE6D-55BD838ACF34')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'465CEDBD-A373-4694-934A-8C3B7C6B2D23')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'4DCFDE53-DE79-4781-9943-1AB9609DEA20')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'51EC51FB-3D94-4BA2-AE95-3F7C99F965FE')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'523C6348-ECDA-4A19-8A64-B990AE3FCCF1')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'55C26BEA-D7AF-4961-B1F9-C09C06129B2A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'563569EE-3418-40A2-B3BB-491F67640BE2')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'58D50708-C82B-4D9E-A04A-B4328EEC5000')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'60B00935-B584-4CB2-ABB4-2EDC5CAE1BF5')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'635E1B34-4006-4077-9A43-DA02F542E8AD')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'64E0D429-D2C3-4245-BA0B-5BF1874BF246')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'67F7B143-1680-410B-B9B0-CD517D6FDE12')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'691AC998-90B4-47BF-AF6C-2F6B1EF418FD')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'6AA63970-1F15-4F42-8075-A3F706D9EB69')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'6BCCCD9B-0DA7-434A-A6EF-C1AF537BA87B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'6C2A64A3-D03A-43A4-A57D-69927C5A7EDE')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'6D77C666-AA32-4453-B1DF-CF5FA0ED4D76')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'6DD32F90-049A-4AD6-A211-83EE3FF9E895')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'7156A631-7775-49E2-8D14-B7B0CCD02AD5')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'72D2D532-244A-4389-8238-850584D3247B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'732D54ED-85E2-42A8-BC19-F49893216C9C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'733928F5-D917-41B9-A02B-10B9E10C76BC')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'787774E3-94DB-4CFF-8CE7-033EE627F1F0')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'794C0169-2080-4EFD-AFD6-04D53505AFC4')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'7AB569D0-AAEF-422B-B52C-B8CFB0CBB2DB')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'7DA76E28-EEAD-4B56-A847-F7610D92A7D5')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'7DC0F873-7BE7-4F25-8CDE-0E0A999B9D26')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'85894C9B-D1A5-436F-A131-D9D3AE6569D5')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'86A39945-85BB-4E6E-B7DA-164E0CEC43D2')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'86CEAA1A-DA55-4D90-980C-F4B393EF1C62')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'8735E083-575D-4AF5-94ED-AE4E7AFD2E17')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'8C5064B9-9EAF-466C-8543-91B0FA4CCE08')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'8CEF3587-A9F2-46BC-A31A-A7DABBD61F9B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'8E25049E-6952-46DF-8068-4F4C46A0AE5E')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'8FB87F83-6336-477D-A088-C085327954A7')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'90B404BB-0F42-4D8F-9234-7B41D96AB679')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'98AE81B2-1575-498A-91C4-16B1D84F7C2C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'991C4FBB-67EE-42BA-9152-42E36D7C0AF0')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'99480005-0567-48B2-BBFB-9974F0B23C1A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'9A5D914F-EE72-4507-8C4B-FBEA254514DF')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'9C7417D6-EE2F-421C-88BC-C508CD17E970')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'9DDE7684-93B4-4124-9643-772F96F99190')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'9DFD8BB9-0EDC-4CA8-B08A-7629417E6B58')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'9FCE096A-352C-4AA4-AEE4-A635EABA8727')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'A673CF7C-805C-43AF-9158-5EEFECECC4D6')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'A8382C2C-4BBA-43F6-B71E-10439959E6A3')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'A908715D-4EA7-4745-B43D-EAD2456ED8E9')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'AA2A41AE-9032-43E2-8A1F-88921F971D23')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'AB6573A7-AC61-456E-BC70-4E6AA530037D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'AB88014E-CB86-443A-9C7C-6EC2374B1DBA')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'ADC5B60D-C899-40E7-ACE2-D1E781CE1165')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'AE5E6126-252E-4CEF-B0EF-BCA71C0A5C1B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'AF4942F4-73AA-441A-9687-6E6CC4CEEB6A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'AFE9186D-5420-4985-871B-4DF088DC93C9')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'B17F1C8E-CC26-48E8-B89F-D09D00DD9A2D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'B2330DB9-14F7-46D3-8F37-BFC1EC3E3AA9')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'B6BE9B4C-94DD-45ED-8AD5-20F71569B706')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'B8AB5460-C685-44AF-B508-3959F67B4927')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'B92408CD-1FDB-49C1-80A4-4E5ADBAEDCE0')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'BA2C6EC0-58BC-47A4-B6F1-2F9990086091')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'BEFFB554-5CC8-4C76-9861-7B3FBB864500')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'BF27F60E-C46E-4D3A-B203-A5EDF6FA501D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C1E4A346-B003-414A-BC31-3E3AAB30FF2C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C20074A4-2823-4C22-9B6C-E899B58420D3')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C28EE9A8-FAAA-46F9-B8CC-ACBAB5369473')
GO
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C4FE0A0B-E6C0-4028-8796-F0EF2BD4F79F')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C54EBE29-75EB-4C9B-83EE-66E23363F0E7')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C73504BF-EBC1-4667-8B27-201BCE7F1D6B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C75DBF6F-23A0-468A-B51E-4C8F88994A2A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C7ADCE32-A20E-4C2A-BDEE-58ECC4610A2B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C9258D9A-EAFB-437A-8C9E-04FDA203B46E')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C9C225AD-C12A-4C34-BD2A-1BEA0D095B98')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'D184E803-DD2A-4FB3-81A9-0E4C1CE12F9E')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'D386A437-0314-4E04-B77E-F53E1DE3F00E')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'D3B7074E-A6B4-4980-BD39-8E8DC12B7A72')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'D3FABE3A-C2D5-4686-90F8-5A00C3627876')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'D84F073C-419B-4B90-B300-A644CACEB71C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'D8C05581-5242-4C59-9556-97984FD3EA9D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'D93714E5-B43B-485A-849C-1A245FC385D1')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'DB5A6DBE-C1EB-4000-8527-8DA5626A38F4')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'DECC7FDB-E906-461B-82EC-4E20B1CCC257')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'E05607E2-E389-42F3-8033-916EF1B6A0FE')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'E384055C-997D-4834-B8B9-EE25EF95796C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'E3E2309F-F946-4647-AD02-4FBE31B0DD19')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'E4D33EDA-E42C-478C-920F-0A4CD3E3432B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'E5895E41-7EEE-4541-831A-4DCAE426AB7C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'E81D4EFF-ADE4-4AA9-A468-B28409C300D2')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'EB57E42B-F8AE-45B6-B162-43E353700CE0')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'F32146EA-2B99-4590-9B4A-65ACFC6B3F8D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'F8885E65-72A3-461F-B782-6DE867A712B0')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'FB7516AF-38ED-4076-921F-641461C04366')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'FBC0DEF1-BA34-4F1A-B19F-847EE8355351')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'FD0ACDFD-D72E-40B9-B208-B03205B00024')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'FD8D4BEA-6EEE-46FB-9CB7-9315A553CAD3')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'FE23E78A-FF09-4F6D-A531-7F23CD1404E0')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'0A144312-7A14-48AC-8179-3728685AAF4C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'1C3D609E-435A-480A-B427-573F0BBB0142')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'1F6965F7-2F0F-4FCB-8D91-0C0ED7542520')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'6DD32F90-049A-4AD6-A211-83EE3FF9E895')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'7156A631-7775-49E2-8D14-B7B0CCD02AD5')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'90B404BB-0F42-4D8F-9234-7B41D96AB679')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'AA2A41AE-9032-43E2-8A1F-88921F971D23')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'AFE9186D-5420-4985-871B-4DF088DC93C9')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'BA2C6EC0-58BC-47A4-B6F1-2F9990086091')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'C9C225AD-C12A-4C34-BD2A-1BEA0D095B98')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'E384055C-997D-4834-B8B9-EE25EF95796C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'FD8D4BEA-6EEE-46FB-9CB7-9315A553CAD3')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'1004A870-B62E-4DD2-A54F-712B7552359D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'15FF5087-9B95-4BCC-943C-B5EA2F4E4FD8')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'1A234409-3FA9-4765-A5B4-A3878433108F')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'1AFDDEC4-C759-4798-9B5B-FA612D076E82')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'1E5F8877-31EA-4BDD-99DF-2B8F5CFFE7AF')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'217FBB33-6CF5-440A-A8B9-B3BFA0E86F72')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'356850B1-762E-4FB6-B7E0-A07C8A28DD63')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'39C7F908-86B3-41B7-91D7-50004878473C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'465CEDBD-A373-4694-934A-8C3B7C6B2D23')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'5F5C4574-ACCD-411F-AEAB-210B97B302B8')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'64E0D429-D2C3-4245-BA0B-5BF1874BF246')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'6AA63970-1F15-4F42-8075-A3F706D9EB69')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'6BCCCD9B-0DA7-434A-A6EF-C1AF537BA87B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'794C0169-2080-4EFD-AFD6-04D53505AFC4')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'8735E083-575D-4AF5-94ED-AE4E7AFD2E17')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'8E25049E-6952-46DF-8068-4F4C46A0AE5E')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'98AE81B2-1575-498A-91C4-16B1D84F7C2C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'AB88014E-CB86-443A-9C7C-6EC2374B1DBA')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'AF4942F4-73AA-441A-9687-6E6CC4CEEB6A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'BEFFB554-5CC8-4C76-9861-7B3FBB864500')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'C28EE9A8-FAAA-46F9-B8CC-ACBAB5369473')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'C9258D9A-EAFB-437A-8C9E-04FDA203B46E')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'E05607E2-E389-42F3-8033-916EF1B6A0FE')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'E5895E41-7EEE-4541-831A-4DCAE426AB7C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'FE23E78A-FF09-4F6D-A531-7F23CD1404E0')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'563569EE-3418-40A2-B3BB-491F67640BE2')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'563569EE-3418-40A2-B3BB-491F67640BE2')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'548AD542-0DA5-4CBA-ADAA-7E1B6AD61690')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'938BAFBF-22DF-40A0-8ACC-E2F467E96F90')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'3E1113DC-5F39-422B-8676-9CE2737FB4CA')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'479EF456-9225-4B25-83EC-2FBCD1461BC4')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'3E1113DC-5F39-422B-8676-9CE2737FB4CA')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'479EF456-9225-4B25-83EC-2FBCD1461BC4')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'32709C83-D5F8-437A-A208-4559646F7E42')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'938BAFBF-22DF-40A0-8ACC-E2F467E96F90')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'8AAE46F0-7B8C-493D-9BBF-B07C5CBDD7C1')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'1AFDDEC4-C759-4798-9B5B-FA612D076E82')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'5F5C4574-ACCD-411F-AEAB-210B97B302B8')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'8AAE46F0-7B8C-493D-9BBF-B07C5CBDD7C1')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'938BAFBF-22DF-40A0-8ACC-E2F467E96F90')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'FDDCAFF0-7702-4869-96F1-E2084D524DEE')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'FDDCAFF0-7702-4869-96F1-E2084D524DEE')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'635E1B34-4006-4077-9A43-DA02F542E8AD')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'1004A870-B62E-4DD2-A54F-712B7552359D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'15FF5087-9B95-4BCC-943C-B5EA2F4E4FD8')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'1A234409-3FA9-4765-A5B4-A3878433108F')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'1AFDDEC4-C759-4798-9B5B-FA612D076E82')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'1E5F8877-31EA-4BDD-99DF-2B8F5CFFE7AF')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'217FBB33-6CF5-440A-A8B9-B3BFA0E86F72')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'356850B1-762E-4FB6-B7E0-A07C8A28DD63')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'39C7F908-86B3-41B7-91D7-50004878473C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'465CEDBD-A373-4694-934A-8C3B7C6B2D23')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'5F5C4574-ACCD-411F-AEAB-210B97B302B8')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'64E0D429-D2C3-4245-BA0B-5BF1874BF246')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'6AA63970-1F15-4F42-8075-A3F706D9EB69')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'6BCCCD9B-0DA7-434A-A6EF-C1AF537BA87B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'794C0169-2080-4EFD-AFD6-04D53505AFC4')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'8735E083-575D-4AF5-94ED-AE4E7AFD2E17')
GO
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'8E25049E-6952-46DF-8068-4F4C46A0AE5E')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'98AE81B2-1575-498A-91C4-16B1D84F7C2C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'AB88014E-CB86-443A-9C7C-6EC2374B1DBA')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'AF4942F4-73AA-441A-9687-6E6CC4CEEB6A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'BEFFB554-5CC8-4C76-9861-7B3FBB864500')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'C28EE9A8-FAAA-46F9-B8CC-ACBAB5369473')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'C9258D9A-EAFB-437A-8C9E-04FDA203B46E')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'E05607E2-E389-42F3-8033-916EF1B6A0FE')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'E5895E41-7EEE-4541-831A-4DCAE426AB7C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'FE23E78A-FF09-4F6D-A531-7F23CD1404E0')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'1F6965F7-2F0F-4FCB-8D91-0C0ED7542520')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'548AD542-0DA5-4CBA-ADAA-7E1B6AD61690')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'7156A631-7775-49E2-8D14-B7B0CCD02AD5')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'8AAE46F0-7B8C-493D-9BBF-B07C5CBDD7C1')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'AFE9186D-5420-4985-871B-4DF088DC93C9')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'29BF554E-CAC6-48AC-A05E-88B55EBEF0A8')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'4D4C2864-7900-4570-A0F8-ABE5961C8135')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'08624617-902A-4FEB-9D5F-EDBA9519485D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'0A144312-7A14-48AC-8179-3728685AAF4C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'0A96E625-2991-46A9-ADD2-4C7ACF876144')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'0B78E831-3752-4547-A0F7-C2CE2DD8891B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'0DDD389F-BAAF-42A7-80A1-9A3775A1C252')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'0F371E03-E1A9-4ED7-AB9D-7FEC4B6C585A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'0FC4465F-2F9E-4F64-B3A2-A3AB90B5D194')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'1004A870-B62E-4DD2-A54F-712B7552359D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'103B88A8-6EAA-4FB9-9DD1-B7D44C25DE25')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'14502C57-6B5A-4611-B08D-EBA91D34621A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'156CE29E-214C-42B8-A7CC-1768FDB27BBF')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'15FF5087-9B95-4BCC-943C-B5EA2F4E4FD8')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'16252B7D-1E01-4CD5-86EA-86397C660150')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'1A234409-3FA9-4765-A5B4-A3878433108F')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'1BE62AD0-C014-4A0F-8B6C-B3224C47509B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'1C3D609E-435A-480A-B427-573F0BBB0142')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'1E5F8877-31EA-4BDD-99DF-2B8F5CFFE7AF')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'1E5FC3B6-9D59-4E16-9018-9094C12EF05C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'1F6965F7-2F0F-4FCB-8D91-0C0ED7542520')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'2030CCB2-7CD8-4D63-ADEF-E226CF092A9A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'20A844BD-71AE-471A-AEC1-D3FC4D1CD12C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'217FBB33-6CF5-440A-A8B9-B3BFA0E86F72')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'2956124E-2B9B-47E2-A815-B75C0964A2BC')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'2F62EEA8-95FC-4655-A19D-D6FA10296514')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'3137A55F-7008-4D2A-816F-6758558523FC')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'32709C83-D5F8-437A-A208-4559646F7E42')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'32DD3EED-FDB4-40C9-AC32-B3AF639E4664')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'3363672E-0604-47D6-A7E6-9AB1BC139DAA')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'356850B1-762E-4FB6-B7E0-A07C8A28DD63')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'384420DC-4BC3-4531-977E-6644EF2EE86C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'39013242-78B2-4DD0-8F72-328CDCA88BA4')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'39C7F908-86B3-41B7-91D7-50004878473C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'3E8E7881-5B4C-44CB-914E-64F08B73A2CD')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'41F3A36A-3F76-48BE-8A1F-29B3386CA880')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'4635EDBE-7345-4DF5-969E-6300698DDE86')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'46402533-EE8F-4FD9-AE6D-55BD838ACF34')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'465CEDBD-A373-4694-934A-8C3B7C6B2D23')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'4DCFDE53-DE79-4781-9943-1AB9609DEA20')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'51EC51FB-3D94-4BA2-AE95-3F7C99F965FE')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'523C6348-ECDA-4A19-8A64-B990AE3FCCF1')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'55C26BEA-D7AF-4961-B1F9-C09C06129B2A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'563569EE-3418-40A2-B3BB-491F67640BE2')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'58D50708-C82B-4D9E-A04A-B4328EEC5000')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'60B00935-B584-4CB2-ABB4-2EDC5CAE1BF5')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'635E1B34-4006-4077-9A43-DA02F542E8AD')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'64E0D429-D2C3-4245-BA0B-5BF1874BF246')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'67F7B143-1680-410B-B9B0-CD517D6FDE12')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'691AC998-90B4-47BF-AF6C-2F6B1EF418FD')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'6AA63970-1F15-4F42-8075-A3F706D9EB69')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'6BCCCD9B-0DA7-434A-A6EF-C1AF537BA87B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'6C2A64A3-D03A-43A4-A57D-69927C5A7EDE')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'6D77C666-AA32-4453-B1DF-CF5FA0ED4D76')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'6DD32F90-049A-4AD6-A211-83EE3FF9E895')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'7156A631-7775-49E2-8D14-B7B0CCD02AD5')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'72D2D532-244A-4389-8238-850584D3247B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'732D54ED-85E2-42A8-BC19-F49893216C9C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'733928F5-D917-41B9-A02B-10B9E10C76BC')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'787774E3-94DB-4CFF-8CE7-033EE627F1F0')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'794C0169-2080-4EFD-AFD6-04D53505AFC4')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'7AB569D0-AAEF-422B-B52C-B8CFB0CBB2DB')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'7DA76E28-EEAD-4B56-A847-F7610D92A7D5')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'7DC0F873-7BE7-4F25-8CDE-0E0A999B9D26')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'85894C9B-D1A5-436F-A131-D9D3AE6569D5')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'86A39945-85BB-4E6E-B7DA-164E0CEC43D2')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'86CEAA1A-DA55-4D90-980C-F4B393EF1C62')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'8735E083-575D-4AF5-94ED-AE4E7AFD2E17')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'8C5064B9-9EAF-466C-8543-91B0FA4CCE08')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'8CEF3587-A9F2-46BC-A31A-A7DABBD61F9B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'8E25049E-6952-46DF-8068-4F4C46A0AE5E')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'8FB87F83-6336-477D-A088-C085327954A7')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'90B404BB-0F42-4D8F-9234-7B41D96AB679')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'98AE81B2-1575-498A-91C4-16B1D84F7C2C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'991C4FBB-67EE-42BA-9152-42E36D7C0AF0')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'99480005-0567-48B2-BBFB-9974F0B23C1A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'9A5D914F-EE72-4507-8C4B-FBEA254514DF')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'9C7417D6-EE2F-421C-88BC-C508CD17E970')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'9DDE7684-93B4-4124-9643-772F96F99190')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'9DFD8BB9-0EDC-4CA8-B08A-7629417E6B58')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'9FCE096A-352C-4AA4-AEE4-A635EABA8727')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'A673CF7C-805C-43AF-9158-5EEFECECC4D6')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'A8382C2C-4BBA-43F6-B71E-10439959E6A3')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'A908715D-4EA7-4745-B43D-EAD2456ED8E9')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'AA2A41AE-9032-43E2-8A1F-88921F971D23')
GO
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'AB6573A7-AC61-456E-BC70-4E6AA530037D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'AB88014E-CB86-443A-9C7C-6EC2374B1DBA')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'ADC5B60D-C899-40E7-ACE2-D1E781CE1165')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'AE5E6126-252E-4CEF-B0EF-BCA71C0A5C1B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'AF4942F4-73AA-441A-9687-6E6CC4CEEB6A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'AFE9186D-5420-4985-871B-4DF088DC93C9')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'B17F1C8E-CC26-48E8-B89F-D09D00DD9A2D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'B2330DB9-14F7-46D3-8F37-BFC1EC3E3AA9')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'B6BE9B4C-94DD-45ED-8AD5-20F71569B706')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'B8AB5460-C685-44AF-B508-3959F67B4927')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'B92408CD-1FDB-49C1-80A4-4E5ADBAEDCE0')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'BA2C6EC0-58BC-47A4-B6F1-2F9990086091')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'BEFFB554-5CC8-4C76-9861-7B3FBB864500')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'BF27F60E-C46E-4D3A-B203-A5EDF6FA501D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C1E4A346-B003-414A-BC31-3E3AAB30FF2C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C20074A4-2823-4C22-9B6C-E899B58420D3')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C28EE9A8-FAAA-46F9-B8CC-ACBAB5369473')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C4FE0A0B-E6C0-4028-8796-F0EF2BD4F79F')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C54EBE29-75EB-4C9B-83EE-66E23363F0E7')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C73504BF-EBC1-4667-8B27-201BCE7F1D6B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C75DBF6F-23A0-468A-B51E-4C8F88994A2A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C7ADCE32-A20E-4C2A-BDEE-58ECC4610A2B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C9258D9A-EAFB-437A-8C9E-04FDA203B46E')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C9C225AD-C12A-4C34-BD2A-1BEA0D095B98')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'D184E803-DD2A-4FB3-81A9-0E4C1CE12F9E')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'D386A437-0314-4E04-B77E-F53E1DE3F00E')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'D3B7074E-A6B4-4980-BD39-8E8DC12B7A72')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'D3FABE3A-C2D5-4686-90F8-5A00C3627876')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'D84F073C-419B-4B90-B300-A644CACEB71C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'D8C05581-5242-4C59-9556-97984FD3EA9D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'D93714E5-B43B-485A-849C-1A245FC385D1')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'DB5A6DBE-C1EB-4000-8527-8DA5626A38F4')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'DECC7FDB-E906-461B-82EC-4E20B1CCC257')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'E05607E2-E389-42F3-8033-916EF1B6A0FE')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'E384055C-997D-4834-B8B9-EE25EF95796C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'E3E2309F-F946-4647-AD02-4FBE31B0DD19')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'E4D33EDA-E42C-478C-920F-0A4CD3E3432B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'E5895E41-7EEE-4541-831A-4DCAE426AB7C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'E81D4EFF-ADE4-4AA9-A468-B28409C300D2')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'EB57E42B-F8AE-45B6-B162-43E353700CE0')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'F32146EA-2B99-4590-9B4A-65ACFC6B3F8D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'F8885E65-72A3-461F-B782-6DE867A712B0')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'FB7516AF-38ED-4076-921F-641461C04366')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'FBC0DEF1-BA34-4F1A-B19F-847EE8355351')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'FD0ACDFD-D72E-40B9-B208-B03205B00024')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'FD8D4BEA-6EEE-46FB-9CB7-9315A553CAD3')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'FE23E78A-FF09-4F6D-A531-7F23CD1404E0')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'0A144312-7A14-48AC-8179-3728685AAF4C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'1C3D609E-435A-480A-B427-573F0BBB0142')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'1F6965F7-2F0F-4FCB-8D91-0C0ED7542520')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'6DD32F90-049A-4AD6-A211-83EE3FF9E895')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'7156A631-7775-49E2-8D14-B7B0CCD02AD5')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'90B404BB-0F42-4D8F-9234-7B41D96AB679')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'AA2A41AE-9032-43E2-8A1F-88921F971D23')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'AFE9186D-5420-4985-871B-4DF088DC93C9')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'BA2C6EC0-58BC-47A4-B6F1-2F9990086091')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'C9C225AD-C12A-4C34-BD2A-1BEA0D095B98')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'E384055C-997D-4834-B8B9-EE25EF95796C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'FD8D4BEA-6EEE-46FB-9CB7-9315A553CAD3')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'1004A870-B62E-4DD2-A54F-712B7552359D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'15FF5087-9B95-4BCC-943C-B5EA2F4E4FD8')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'1A234409-3FA9-4765-A5B4-A3878433108F')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'1AFDDEC4-C759-4798-9B5B-FA612D076E82')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'1E5F8877-31EA-4BDD-99DF-2B8F5CFFE7AF')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'217FBB33-6CF5-440A-A8B9-B3BFA0E86F72')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'356850B1-762E-4FB6-B7E0-A07C8A28DD63')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'39C7F908-86B3-41B7-91D7-50004878473C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'465CEDBD-A373-4694-934A-8C3B7C6B2D23')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'5F5C4574-ACCD-411F-AEAB-210B97B302B8')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'64E0D429-D2C3-4245-BA0B-5BF1874BF246')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'6AA63970-1F15-4F42-8075-A3F706D9EB69')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'6BCCCD9B-0DA7-434A-A6EF-C1AF537BA87B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'794C0169-2080-4EFD-AFD6-04D53505AFC4')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'8735E083-575D-4AF5-94ED-AE4E7AFD2E17')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'8E25049E-6952-46DF-8068-4F4C46A0AE5E')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'98AE81B2-1575-498A-91C4-16B1D84F7C2C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'AB88014E-CB86-443A-9C7C-6EC2374B1DBA')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'AF4942F4-73AA-441A-9687-6E6CC4CEEB6A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'BEFFB554-5CC8-4C76-9861-7B3FBB864500')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'C28EE9A8-FAAA-46F9-B8CC-ACBAB5369473')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'C9258D9A-EAFB-437A-8C9E-04FDA203B46E')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'E05607E2-E389-42F3-8033-916EF1B6A0FE')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'E5895E41-7EEE-4541-831A-4DCAE426AB7C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'FE23E78A-FF09-4F6D-A531-7F23CD1404E0')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'563569EE-3418-40A2-B3BB-491F67640BE2')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'563569EE-3418-40A2-B3BB-491F67640BE2')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'548AD542-0DA5-4CBA-ADAA-7E1B6AD61690')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'938BAFBF-22DF-40A0-8ACC-E2F467E96F90')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'3E1113DC-5F39-422B-8676-9CE2737FB4CA')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'479EF456-9225-4B25-83EC-2FBCD1461BC4')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'3E1113DC-5F39-422B-8676-9CE2737FB4CA')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'479EF456-9225-4B25-83EC-2FBCD1461BC4')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'32709C83-D5F8-437A-A208-4559646F7E42')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'938BAFBF-22DF-40A0-8ACC-E2F467E96F90')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'8AAE46F0-7B8C-493D-9BBF-B07C5CBDD7C1')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'1AFDDEC4-C759-4798-9B5B-FA612D076E82')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'5F5C4574-ACCD-411F-AEAB-210B97B302B8')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'8AAE46F0-7B8C-493D-9BBF-B07C5CBDD7C1')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'938BAFBF-22DF-40A0-8ACC-E2F467E96F90')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'FDDCAFF0-7702-4869-96F1-E2084D524DEE')
GO
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'FDDCAFF0-7702-4869-96F1-E2084D524DEE')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'635E1B34-4006-4077-9A43-DA02F542E8AD')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'1004A870-B62E-4DD2-A54F-712B7552359D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'15FF5087-9B95-4BCC-943C-B5EA2F4E4FD8')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'1A234409-3FA9-4765-A5B4-A3878433108F')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'1AFDDEC4-C759-4798-9B5B-FA612D076E82')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'1E5F8877-31EA-4BDD-99DF-2B8F5CFFE7AF')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'217FBB33-6CF5-440A-A8B9-B3BFA0E86F72')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'356850B1-762E-4FB6-B7E0-A07C8A28DD63')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'39C7F908-86B3-41B7-91D7-50004878473C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'465CEDBD-A373-4694-934A-8C3B7C6B2D23')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'5F5C4574-ACCD-411F-AEAB-210B97B302B8')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'64E0D429-D2C3-4245-BA0B-5BF1874BF246')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'6AA63970-1F15-4F42-8075-A3F706D9EB69')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'6BCCCD9B-0DA7-434A-A6EF-C1AF537BA87B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'794C0169-2080-4EFD-AFD6-04D53505AFC4')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'8735E083-575D-4AF5-94ED-AE4E7AFD2E17')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'8E25049E-6952-46DF-8068-4F4C46A0AE5E')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'98AE81B2-1575-498A-91C4-16B1D84F7C2C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'AB88014E-CB86-443A-9C7C-6EC2374B1DBA')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'AF4942F4-73AA-441A-9687-6E6CC4CEEB6A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'BEFFB554-5CC8-4C76-9861-7B3FBB864500')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'C28EE9A8-FAAA-46F9-B8CC-ACBAB5369473')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'C9258D9A-EAFB-437A-8C9E-04FDA203B46E')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'E05607E2-E389-42F3-8033-916EF1B6A0FE')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'E5895E41-7EEE-4541-831A-4DCAE426AB7C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'FE23E78A-FF09-4F6D-A531-7F23CD1404E0')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'1F6965F7-2F0F-4FCB-8D91-0C0ED7542520')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'548AD542-0DA5-4CBA-ADAA-7E1B6AD61690')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'7156A631-7775-49E2-8D14-B7B0CCD02AD5')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'8AAE46F0-7B8C-493D-9BBF-B07C5CBDD7C1')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'AFE9186D-5420-4985-871B-4DF088DC93C9')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'29BF554E-CAC6-48AC-A05E-88B55EBEF0A8')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'4D4C2864-7900-4570-A0F8-ABE5961C8135')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'08624617-902A-4FEB-9D5F-EDBA9519485D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'0A144312-7A14-48AC-8179-3728685AAF4C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'0A96E625-2991-46A9-ADD2-4C7ACF876144')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'0B78E831-3752-4547-A0F7-C2CE2DD8891B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'0DDD389F-BAAF-42A7-80A1-9A3775A1C252')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'0F371E03-E1A9-4ED7-AB9D-7FEC4B6C585A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'0FC4465F-2F9E-4F64-B3A2-A3AB90B5D194')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'1004A870-B62E-4DD2-A54F-712B7552359D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'103B88A8-6EAA-4FB9-9DD1-B7D44C25DE25')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'14502C57-6B5A-4611-B08D-EBA91D34621A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'156CE29E-214C-42B8-A7CC-1768FDB27BBF')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'15FF5087-9B95-4BCC-943C-B5EA2F4E4FD8')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'16252B7D-1E01-4CD5-86EA-86397C660150')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'1A234409-3FA9-4765-A5B4-A3878433108F')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'1BE62AD0-C014-4A0F-8B6C-B3224C47509B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'1C3D609E-435A-480A-B427-573F0BBB0142')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'1E5F8877-31EA-4BDD-99DF-2B8F5CFFE7AF')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'1E5FC3B6-9D59-4E16-9018-9094C12EF05C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'1F6965F7-2F0F-4FCB-8D91-0C0ED7542520')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'2030CCB2-7CD8-4D63-ADEF-E226CF092A9A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'20A844BD-71AE-471A-AEC1-D3FC4D1CD12C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'217FBB33-6CF5-440A-A8B9-B3BFA0E86F72')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'2956124E-2B9B-47E2-A815-B75C0964A2BC')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'2F62EEA8-95FC-4655-A19D-D6FA10296514')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'3137A55F-7008-4D2A-816F-6758558523FC')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'32709C83-D5F8-437A-A208-4559646F7E42')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'32DD3EED-FDB4-40C9-AC32-B3AF639E4664')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'3363672E-0604-47D6-A7E6-9AB1BC139DAA')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'356850B1-762E-4FB6-B7E0-A07C8A28DD63')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'384420DC-4BC3-4531-977E-6644EF2EE86C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'39013242-78B2-4DD0-8F72-328CDCA88BA4')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'39C7F908-86B3-41B7-91D7-50004878473C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'3E8E7881-5B4C-44CB-914E-64F08B73A2CD')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'41F3A36A-3F76-48BE-8A1F-29B3386CA880')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'4635EDBE-7345-4DF5-969E-6300698DDE86')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'46402533-EE8F-4FD9-AE6D-55BD838ACF34')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'465CEDBD-A373-4694-934A-8C3B7C6B2D23')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'4DCFDE53-DE79-4781-9943-1AB9609DEA20')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'51EC51FB-3D94-4BA2-AE95-3F7C99F965FE')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'523C6348-ECDA-4A19-8A64-B990AE3FCCF1')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'55C26BEA-D7AF-4961-B1F9-C09C06129B2A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'563569EE-3418-40A2-B3BB-491F67640BE2')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'58D50708-C82B-4D9E-A04A-B4328EEC5000')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'60B00935-B584-4CB2-ABB4-2EDC5CAE1BF5')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'635E1B34-4006-4077-9A43-DA02F542E8AD')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'64E0D429-D2C3-4245-BA0B-5BF1874BF246')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'67F7B143-1680-410B-B9B0-CD517D6FDE12')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'691AC998-90B4-47BF-AF6C-2F6B1EF418FD')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'6AA63970-1F15-4F42-8075-A3F706D9EB69')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'6BCCCD9B-0DA7-434A-A6EF-C1AF537BA87B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'6C2A64A3-D03A-43A4-A57D-69927C5A7EDE')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'6D77C666-AA32-4453-B1DF-CF5FA0ED4D76')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'6DD32F90-049A-4AD6-A211-83EE3FF9E895')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'7156A631-7775-49E2-8D14-B7B0CCD02AD5')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'72D2D532-244A-4389-8238-850584D3247B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'732D54ED-85E2-42A8-BC19-F49893216C9C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'733928F5-D917-41B9-A02B-10B9E10C76BC')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'787774E3-94DB-4CFF-8CE7-033EE627F1F0')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'794C0169-2080-4EFD-AFD6-04D53505AFC4')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'7AB569D0-AAEF-422B-B52C-B8CFB0CBB2DB')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'7DA76E28-EEAD-4B56-A847-F7610D92A7D5')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'7DC0F873-7BE7-4F25-8CDE-0E0A999B9D26')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'85894C9B-D1A5-436F-A131-D9D3AE6569D5')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'86A39945-85BB-4E6E-B7DA-164E0CEC43D2')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'86CEAA1A-DA55-4D90-980C-F4B393EF1C62')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'8735E083-575D-4AF5-94ED-AE4E7AFD2E17')
GO
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'8C5064B9-9EAF-466C-8543-91B0FA4CCE08')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'8CEF3587-A9F2-46BC-A31A-A7DABBD61F9B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'8E25049E-6952-46DF-8068-4F4C46A0AE5E')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'8FB87F83-6336-477D-A088-C085327954A7')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'90B404BB-0F42-4D8F-9234-7B41D96AB679')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'98AE81B2-1575-498A-91C4-16B1D84F7C2C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'991C4FBB-67EE-42BA-9152-42E36D7C0AF0')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'99480005-0567-48B2-BBFB-9974F0B23C1A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'9A5D914F-EE72-4507-8C4B-FBEA254514DF')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'9C7417D6-EE2F-421C-88BC-C508CD17E970')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'9DDE7684-93B4-4124-9643-772F96F99190')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'9DFD8BB9-0EDC-4CA8-B08A-7629417E6B58')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'9FCE096A-352C-4AA4-AEE4-A635EABA8727')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'A673CF7C-805C-43AF-9158-5EEFECECC4D6')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'A8382C2C-4BBA-43F6-B71E-10439959E6A3')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'A908715D-4EA7-4745-B43D-EAD2456ED8E9')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'AA2A41AE-9032-43E2-8A1F-88921F971D23')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'AB6573A7-AC61-456E-BC70-4E6AA530037D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'AB88014E-CB86-443A-9C7C-6EC2374B1DBA')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'ADC5B60D-C899-40E7-ACE2-D1E781CE1165')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'AE5E6126-252E-4CEF-B0EF-BCA71C0A5C1B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'AF4942F4-73AA-441A-9687-6E6CC4CEEB6A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'AFE9186D-5420-4985-871B-4DF088DC93C9')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'B17F1C8E-CC26-48E8-B89F-D09D00DD9A2D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'B2330DB9-14F7-46D3-8F37-BFC1EC3E3AA9')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'B6BE9B4C-94DD-45ED-8AD5-20F71569B706')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'B8AB5460-C685-44AF-B508-3959F67B4927')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'B92408CD-1FDB-49C1-80A4-4E5ADBAEDCE0')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'BA2C6EC0-58BC-47A4-B6F1-2F9990086091')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'BEFFB554-5CC8-4C76-9861-7B3FBB864500')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'BF27F60E-C46E-4D3A-B203-A5EDF6FA501D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C1E4A346-B003-414A-BC31-3E3AAB30FF2C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C20074A4-2823-4C22-9B6C-E899B58420D3')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C28EE9A8-FAAA-46F9-B8CC-ACBAB5369473')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C4FE0A0B-E6C0-4028-8796-F0EF2BD4F79F')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C54EBE29-75EB-4C9B-83EE-66E23363F0E7')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C73504BF-EBC1-4667-8B27-201BCE7F1D6B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C75DBF6F-23A0-468A-B51E-4C8F88994A2A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C7ADCE32-A20E-4C2A-BDEE-58ECC4610A2B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C9258D9A-EAFB-437A-8C9E-04FDA203B46E')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C9C225AD-C12A-4C34-BD2A-1BEA0D095B98')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'D184E803-DD2A-4FB3-81A9-0E4C1CE12F9E')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'D386A437-0314-4E04-B77E-F53E1DE3F00E')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'D3B7074E-A6B4-4980-BD39-8E8DC12B7A72')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'D3FABE3A-C2D5-4686-90F8-5A00C3627876')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'D84F073C-419B-4B90-B300-A644CACEB71C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'D8C05581-5242-4C59-9556-97984FD3EA9D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'D93714E5-B43B-485A-849C-1A245FC385D1')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'DB5A6DBE-C1EB-4000-8527-8DA5626A38F4')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'DECC7FDB-E906-461B-82EC-4E20B1CCC257')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'E05607E2-E389-42F3-8033-916EF1B6A0FE')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'E384055C-997D-4834-B8B9-EE25EF95796C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'E3E2309F-F946-4647-AD02-4FBE31B0DD19')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'E4D33EDA-E42C-478C-920F-0A4CD3E3432B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'E5895E41-7EEE-4541-831A-4DCAE426AB7C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'E81D4EFF-ADE4-4AA9-A468-B28409C300D2')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'EB57E42B-F8AE-45B6-B162-43E353700CE0')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'F32146EA-2B99-4590-9B4A-65ACFC6B3F8D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'F8885E65-72A3-461F-B782-6DE867A712B0')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'FB7516AF-38ED-4076-921F-641461C04366')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'FBC0DEF1-BA34-4F1A-B19F-847EE8355351')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'FD0ACDFD-D72E-40B9-B208-B03205B00024')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'FD8D4BEA-6EEE-46FB-9CB7-9315A553CAD3')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'FE23E78A-FF09-4F6D-A531-7F23CD1404E0')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'0A144312-7A14-48AC-8179-3728685AAF4C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'1C3D609E-435A-480A-B427-573F0BBB0142')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'1F6965F7-2F0F-4FCB-8D91-0C0ED7542520')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'6DD32F90-049A-4AD6-A211-83EE3FF9E895')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'7156A631-7775-49E2-8D14-B7B0CCD02AD5')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'90B404BB-0F42-4D8F-9234-7B41D96AB679')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'AA2A41AE-9032-43E2-8A1F-88921F971D23')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'AFE9186D-5420-4985-871B-4DF088DC93C9')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'BA2C6EC0-58BC-47A4-B6F1-2F9990086091')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'C9C225AD-C12A-4C34-BD2A-1BEA0D095B98')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'E384055C-997D-4834-B8B9-EE25EF95796C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'FD8D4BEA-6EEE-46FB-9CB7-9315A553CAD3')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'1004A870-B62E-4DD2-A54F-712B7552359D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'15FF5087-9B95-4BCC-943C-B5EA2F4E4FD8')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'1A234409-3FA9-4765-A5B4-A3878433108F')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'1AFDDEC4-C759-4798-9B5B-FA612D076E82')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'1E5F8877-31EA-4BDD-99DF-2B8F5CFFE7AF')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'217FBB33-6CF5-440A-A8B9-B3BFA0E86F72')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'356850B1-762E-4FB6-B7E0-A07C8A28DD63')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'39C7F908-86B3-41B7-91D7-50004878473C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'465CEDBD-A373-4694-934A-8C3B7C6B2D23')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'5F5C4574-ACCD-411F-AEAB-210B97B302B8')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'64E0D429-D2C3-4245-BA0B-5BF1874BF246')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'6AA63970-1F15-4F42-8075-A3F706D9EB69')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'6BCCCD9B-0DA7-434A-A6EF-C1AF537BA87B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'794C0169-2080-4EFD-AFD6-04D53505AFC4')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'8735E083-575D-4AF5-94ED-AE4E7AFD2E17')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'8E25049E-6952-46DF-8068-4F4C46A0AE5E')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'98AE81B2-1575-498A-91C4-16B1D84F7C2C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'AB88014E-CB86-443A-9C7C-6EC2374B1DBA')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'AF4942F4-73AA-441A-9687-6E6CC4CEEB6A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'BEFFB554-5CC8-4C76-9861-7B3FBB864500')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'C28EE9A8-FAAA-46F9-B8CC-ACBAB5369473')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'C9258D9A-EAFB-437A-8C9E-04FDA203B46E')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'E05607E2-E389-42F3-8033-916EF1B6A0FE')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'E5895E41-7EEE-4541-831A-4DCAE426AB7C')
GO
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'FE23E78A-FF09-4F6D-A531-7F23CD1404E0')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'563569EE-3418-40A2-B3BB-491F67640BE2')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'563569EE-3418-40A2-B3BB-491F67640BE2')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'548AD542-0DA5-4CBA-ADAA-7E1B6AD61690')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'938BAFBF-22DF-40A0-8ACC-E2F467E96F90')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'3E1113DC-5F39-422B-8676-9CE2737FB4CA')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'479EF456-9225-4B25-83EC-2FBCD1461BC4')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'3E1113DC-5F39-422B-8676-9CE2737FB4CA')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'479EF456-9225-4B25-83EC-2FBCD1461BC4')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'32709C83-D5F8-437A-A208-4559646F7E42')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'938BAFBF-22DF-40A0-8ACC-E2F467E96F90')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'8AAE46F0-7B8C-493D-9BBF-B07C5CBDD7C1')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'1AFDDEC4-C759-4798-9B5B-FA612D076E82')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'5F5C4574-ACCD-411F-AEAB-210B97B302B8')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'8AAE46F0-7B8C-493D-9BBF-B07C5CBDD7C1')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'938BAFBF-22DF-40A0-8ACC-E2F467E96F90')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'FDDCAFF0-7702-4869-96F1-E2084D524DEE')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'FDDCAFF0-7702-4869-96F1-E2084D524DEE')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'635E1B34-4006-4077-9A43-DA02F542E8AD')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'1004A870-B62E-4DD2-A54F-712B7552359D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'15FF5087-9B95-4BCC-943C-B5EA2F4E4FD8')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'1A234409-3FA9-4765-A5B4-A3878433108F')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'1AFDDEC4-C759-4798-9B5B-FA612D076E82')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'1E5F8877-31EA-4BDD-99DF-2B8F5CFFE7AF')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'217FBB33-6CF5-440A-A8B9-B3BFA0E86F72')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'356850B1-762E-4FB6-B7E0-A07C8A28DD63')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'39C7F908-86B3-41B7-91D7-50004878473C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'465CEDBD-A373-4694-934A-8C3B7C6B2D23')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'5F5C4574-ACCD-411F-AEAB-210B97B302B8')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'64E0D429-D2C3-4245-BA0B-5BF1874BF246')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'6AA63970-1F15-4F42-8075-A3F706D9EB69')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'6BCCCD9B-0DA7-434A-A6EF-C1AF537BA87B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'794C0169-2080-4EFD-AFD6-04D53505AFC4')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'8735E083-575D-4AF5-94ED-AE4E7AFD2E17')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'8E25049E-6952-46DF-8068-4F4C46A0AE5E')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'98AE81B2-1575-498A-91C4-16B1D84F7C2C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'AB88014E-CB86-443A-9C7C-6EC2374B1DBA')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'AF4942F4-73AA-441A-9687-6E6CC4CEEB6A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'BEFFB554-5CC8-4C76-9861-7B3FBB864500')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'C28EE9A8-FAAA-46F9-B8CC-ACBAB5369473')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'C9258D9A-EAFB-437A-8C9E-04FDA203B46E')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'E05607E2-E389-42F3-8033-916EF1B6A0FE')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'E5895E41-7EEE-4541-831A-4DCAE426AB7C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'FE23E78A-FF09-4F6D-A531-7F23CD1404E0')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'1F6965F7-2F0F-4FCB-8D91-0C0ED7542520')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'548AD542-0DA5-4CBA-ADAA-7E1B6AD61690')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'7156A631-7775-49E2-8D14-B7B0CCD02AD5')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'8AAE46F0-7B8C-493D-9BBF-B07C5CBDD7C1')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'AFE9186D-5420-4985-871B-4DF088DC93C9')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'29BF554E-CAC6-48AC-A05E-88B55EBEF0A8')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'4D4C2864-7900-4570-A0F8-ABE5961C8135')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'08624617-902A-4FEB-9D5F-EDBA9519485D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'0A144312-7A14-48AC-8179-3728685AAF4C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'0A96E625-2991-46A9-ADD2-4C7ACF876144')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'0B78E831-3752-4547-A0F7-C2CE2DD8891B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'0DDD389F-BAAF-42A7-80A1-9A3775A1C252')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'0F371E03-E1A9-4ED7-AB9D-7FEC4B6C585A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'0FC4465F-2F9E-4F64-B3A2-A3AB90B5D194')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'1004A870-B62E-4DD2-A54F-712B7552359D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'103B88A8-6EAA-4FB9-9DD1-B7D44C25DE25')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'14502C57-6B5A-4611-B08D-EBA91D34621A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'156CE29E-214C-42B8-A7CC-1768FDB27BBF')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'15FF5087-9B95-4BCC-943C-B5EA2F4E4FD8')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'16252B7D-1E01-4CD5-86EA-86397C660150')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'1A234409-3FA9-4765-A5B4-A3878433108F')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'1BE62AD0-C014-4A0F-8B6C-B3224C47509B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'1C3D609E-435A-480A-B427-573F0BBB0142')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'1E5F8877-31EA-4BDD-99DF-2B8F5CFFE7AF')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'1E5FC3B6-9D59-4E16-9018-9094C12EF05C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'1F6965F7-2F0F-4FCB-8D91-0C0ED7542520')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'2030CCB2-7CD8-4D63-ADEF-E226CF092A9A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'20A844BD-71AE-471A-AEC1-D3FC4D1CD12C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'217FBB33-6CF5-440A-A8B9-B3BFA0E86F72')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'2956124E-2B9B-47E2-A815-B75C0964A2BC')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'2F62EEA8-95FC-4655-A19D-D6FA10296514')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'3137A55F-7008-4D2A-816F-6758558523FC')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'32709C83-D5F8-437A-A208-4559646F7E42')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'32DD3EED-FDB4-40C9-AC32-B3AF639E4664')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'3363672E-0604-47D6-A7E6-9AB1BC139DAA')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'356850B1-762E-4FB6-B7E0-A07C8A28DD63')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'384420DC-4BC3-4531-977E-6644EF2EE86C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'39013242-78B2-4DD0-8F72-328CDCA88BA4')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'39C7F908-86B3-41B7-91D7-50004878473C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'3E8E7881-5B4C-44CB-914E-64F08B73A2CD')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'41F3A36A-3F76-48BE-8A1F-29B3386CA880')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'4635EDBE-7345-4DF5-969E-6300698DDE86')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'46402533-EE8F-4FD9-AE6D-55BD838ACF34')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'465CEDBD-A373-4694-934A-8C3B7C6B2D23')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'4DCFDE53-DE79-4781-9943-1AB9609DEA20')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'51EC51FB-3D94-4BA2-AE95-3F7C99F965FE')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'523C6348-ECDA-4A19-8A64-B990AE3FCCF1')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'55C26BEA-D7AF-4961-B1F9-C09C06129B2A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'563569EE-3418-40A2-B3BB-491F67640BE2')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'58D50708-C82B-4D9E-A04A-B4328EEC5000')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'60B00935-B584-4CB2-ABB4-2EDC5CAE1BF5')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'635E1B34-4006-4077-9A43-DA02F542E8AD')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'64E0D429-D2C3-4245-BA0B-5BF1874BF246')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'67F7B143-1680-410B-B9B0-CD517D6FDE12')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'691AC998-90B4-47BF-AF6C-2F6B1EF418FD')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'6AA63970-1F15-4F42-8075-A3F706D9EB69')
GO
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'6BCCCD9B-0DA7-434A-A6EF-C1AF537BA87B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'6C2A64A3-D03A-43A4-A57D-69927C5A7EDE')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'6D77C666-AA32-4453-B1DF-CF5FA0ED4D76')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'6DD32F90-049A-4AD6-A211-83EE3FF9E895')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'7156A631-7775-49E2-8D14-B7B0CCD02AD5')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'72D2D532-244A-4389-8238-850584D3247B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'732D54ED-85E2-42A8-BC19-F49893216C9C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'733928F5-D917-41B9-A02B-10B9E10C76BC')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'787774E3-94DB-4CFF-8CE7-033EE627F1F0')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'794C0169-2080-4EFD-AFD6-04D53505AFC4')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'7AB569D0-AAEF-422B-B52C-B8CFB0CBB2DB')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'7DA76E28-EEAD-4B56-A847-F7610D92A7D5')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'7DC0F873-7BE7-4F25-8CDE-0E0A999B9D26')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'85894C9B-D1A5-436F-A131-D9D3AE6569D5')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'86A39945-85BB-4E6E-B7DA-164E0CEC43D2')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'86CEAA1A-DA55-4D90-980C-F4B393EF1C62')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'8735E083-575D-4AF5-94ED-AE4E7AFD2E17')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'8C5064B9-9EAF-466C-8543-91B0FA4CCE08')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'8CEF3587-A9F2-46BC-A31A-A7DABBD61F9B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'8E25049E-6952-46DF-8068-4F4C46A0AE5E')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'8FB87F83-6336-477D-A088-C085327954A7')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'90B404BB-0F42-4D8F-9234-7B41D96AB679')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'98AE81B2-1575-498A-91C4-16B1D84F7C2C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'991C4FBB-67EE-42BA-9152-42E36D7C0AF0')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'99480005-0567-48B2-BBFB-9974F0B23C1A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'9A5D914F-EE72-4507-8C4B-FBEA254514DF')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'9C7417D6-EE2F-421C-88BC-C508CD17E970')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'9DDE7684-93B4-4124-9643-772F96F99190')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'9DFD8BB9-0EDC-4CA8-B08A-7629417E6B58')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'9FCE096A-352C-4AA4-AEE4-A635EABA8727')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'A673CF7C-805C-43AF-9158-5EEFECECC4D6')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'A8382C2C-4BBA-43F6-B71E-10439959E6A3')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'A908715D-4EA7-4745-B43D-EAD2456ED8E9')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'AA2A41AE-9032-43E2-8A1F-88921F971D23')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'AB6573A7-AC61-456E-BC70-4E6AA530037D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'AB88014E-CB86-443A-9C7C-6EC2374B1DBA')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'ADC5B60D-C899-40E7-ACE2-D1E781CE1165')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'AE5E6126-252E-4CEF-B0EF-BCA71C0A5C1B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'AF4942F4-73AA-441A-9687-6E6CC4CEEB6A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'AFE9186D-5420-4985-871B-4DF088DC93C9')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'B17F1C8E-CC26-48E8-B89F-D09D00DD9A2D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'B2330DB9-14F7-46D3-8F37-BFC1EC3E3AA9')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'B6BE9B4C-94DD-45ED-8AD5-20F71569B706')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'B8AB5460-C685-44AF-B508-3959F67B4927')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'B92408CD-1FDB-49C1-80A4-4E5ADBAEDCE0')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'BA2C6EC0-58BC-47A4-B6F1-2F9990086091')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'BEFFB554-5CC8-4C76-9861-7B3FBB864500')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'BF27F60E-C46E-4D3A-B203-A5EDF6FA501D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C1E4A346-B003-414A-BC31-3E3AAB30FF2C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C20074A4-2823-4C22-9B6C-E899B58420D3')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C28EE9A8-FAAA-46F9-B8CC-ACBAB5369473')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C4FE0A0B-E6C0-4028-8796-F0EF2BD4F79F')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C54EBE29-75EB-4C9B-83EE-66E23363F0E7')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C73504BF-EBC1-4667-8B27-201BCE7F1D6B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C75DBF6F-23A0-468A-B51E-4C8F88994A2A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C7ADCE32-A20E-4C2A-BDEE-58ECC4610A2B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C9258D9A-EAFB-437A-8C9E-04FDA203B46E')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'C9C225AD-C12A-4C34-BD2A-1BEA0D095B98')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'D184E803-DD2A-4FB3-81A9-0E4C1CE12F9E')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'D386A437-0314-4E04-B77E-F53E1DE3F00E')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'D3B7074E-A6B4-4980-BD39-8E8DC12B7A72')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'D3FABE3A-C2D5-4686-90F8-5A00C3627876')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'D84F073C-419B-4B90-B300-A644CACEB71C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'D8C05581-5242-4C59-9556-97984FD3EA9D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'D93714E5-B43B-485A-849C-1A245FC385D1')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'DB5A6DBE-C1EB-4000-8527-8DA5626A38F4')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'DECC7FDB-E906-461B-82EC-4E20B1CCC257')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'E05607E2-E389-42F3-8033-916EF1B6A0FE')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'E384055C-997D-4834-B8B9-EE25EF95796C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'E3E2309F-F946-4647-AD02-4FBE31B0DD19')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'E4D33EDA-E42C-478C-920F-0A4CD3E3432B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'E5895E41-7EEE-4541-831A-4DCAE426AB7C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'E81D4EFF-ADE4-4AA9-A468-B28409C300D2')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'EB57E42B-F8AE-45B6-B162-43E353700CE0')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'F32146EA-2B99-4590-9B4A-65ACFC6B3F8D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'F8885E65-72A3-461F-B782-6DE867A712B0')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'FB7516AF-38ED-4076-921F-641461C04366')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'FBC0DEF1-BA34-4F1A-B19F-847EE8355351')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'FD0ACDFD-D72E-40B9-B208-B03205B00024')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'FD8D4BEA-6EEE-46FB-9CB7-9315A553CAD3')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'FE23E78A-FF09-4F6D-A531-7F23CD1404E0')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'0A144312-7A14-48AC-8179-3728685AAF4C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'1C3D609E-435A-480A-B427-573F0BBB0142')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'1F6965F7-2F0F-4FCB-8D91-0C0ED7542520')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'6DD32F90-049A-4AD6-A211-83EE3FF9E895')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'7156A631-7775-49E2-8D14-B7B0CCD02AD5')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'90B404BB-0F42-4D8F-9234-7B41D96AB679')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'AA2A41AE-9032-43E2-8A1F-88921F971D23')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'AFE9186D-5420-4985-871B-4DF088DC93C9')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'BA2C6EC0-58BC-47A4-B6F1-2F9990086091')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'C9C225AD-C12A-4C34-BD2A-1BEA0D095B98')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'E384055C-997D-4834-B8B9-EE25EF95796C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'FD8D4BEA-6EEE-46FB-9CB7-9315A553CAD3')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'1004A870-B62E-4DD2-A54F-712B7552359D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'15FF5087-9B95-4BCC-943C-B5EA2F4E4FD8')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'1A234409-3FA9-4765-A5B4-A3878433108F')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'1AFDDEC4-C759-4798-9B5B-FA612D076E82')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'1E5F8877-31EA-4BDD-99DF-2B8F5CFFE7AF')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'217FBB33-6CF5-440A-A8B9-B3BFA0E86F72')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'356850B1-762E-4FB6-B7E0-A07C8A28DD63')
GO
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'39C7F908-86B3-41B7-91D7-50004878473C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'465CEDBD-A373-4694-934A-8C3B7C6B2D23')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'5F5C4574-ACCD-411F-AEAB-210B97B302B8')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'64E0D429-D2C3-4245-BA0B-5BF1874BF246')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'6AA63970-1F15-4F42-8075-A3F706D9EB69')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'6BCCCD9B-0DA7-434A-A6EF-C1AF537BA87B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'794C0169-2080-4EFD-AFD6-04D53505AFC4')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'8735E083-575D-4AF5-94ED-AE4E7AFD2E17')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'8E25049E-6952-46DF-8068-4F4C46A0AE5E')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'98AE81B2-1575-498A-91C4-16B1D84F7C2C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'AB88014E-CB86-443A-9C7C-6EC2374B1DBA')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'AF4942F4-73AA-441A-9687-6E6CC4CEEB6A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'BEFFB554-5CC8-4C76-9861-7B3FBB864500')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'C28EE9A8-FAAA-46F9-B8CC-ACBAB5369473')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'C9258D9A-EAFB-437A-8C9E-04FDA203B46E')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'E05607E2-E389-42F3-8033-916EF1B6A0FE')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'E5895E41-7EEE-4541-831A-4DCAE426AB7C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'FE23E78A-FF09-4F6D-A531-7F23CD1404E0')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'563569EE-3418-40A2-B3BB-491F67640BE2')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'563569EE-3418-40A2-B3BB-491F67640BE2')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'548AD542-0DA5-4CBA-ADAA-7E1B6AD61690')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'938BAFBF-22DF-40A0-8ACC-E2F467E96F90')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'3E1113DC-5F39-422B-8676-9CE2737FB4CA')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'479EF456-9225-4B25-83EC-2FBCD1461BC4')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'3E1113DC-5F39-422B-8676-9CE2737FB4CA')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'479EF456-9225-4B25-83EC-2FBCD1461BC4')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'32709C83-D5F8-437A-A208-4559646F7E42')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'938BAFBF-22DF-40A0-8ACC-E2F467E96F90')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'8AAE46F0-7B8C-493D-9BBF-B07C5CBDD7C1')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'1AFDDEC4-C759-4798-9B5B-FA612D076E82')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'5F5C4574-ACCD-411F-AEAB-210B97B302B8')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'8AAE46F0-7B8C-493D-9BBF-B07C5CBDD7C1')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'938BAFBF-22DF-40A0-8ACC-E2F467E96F90')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'FDDCAFF0-7702-4869-96F1-E2084D524DEE')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'FDDCAFF0-7702-4869-96F1-E2084D524DEE')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', N'635E1B34-4006-4077-9A43-DA02F542E8AD')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'1004A870-B62E-4DD2-A54F-712B7552359D')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'15FF5087-9B95-4BCC-943C-B5EA2F4E4FD8')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'1A234409-3FA9-4765-A5B4-A3878433108F')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'1AFDDEC4-C759-4798-9B5B-FA612D076E82')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'1E5F8877-31EA-4BDD-99DF-2B8F5CFFE7AF')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'217FBB33-6CF5-440A-A8B9-B3BFA0E86F72')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'356850B1-762E-4FB6-B7E0-A07C8A28DD63')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'39C7F908-86B3-41B7-91D7-50004878473C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'465CEDBD-A373-4694-934A-8C3B7C6B2D23')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'5F5C4574-ACCD-411F-AEAB-210B97B302B8')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'64E0D429-D2C3-4245-BA0B-5BF1874BF246')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'6AA63970-1F15-4F42-8075-A3F706D9EB69')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'6BCCCD9B-0DA7-434A-A6EF-C1AF537BA87B')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'794C0169-2080-4EFD-AFD6-04D53505AFC4')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'8735E083-575D-4AF5-94ED-AE4E7AFD2E17')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'8E25049E-6952-46DF-8068-4F4C46A0AE5E')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'98AE81B2-1575-498A-91C4-16B1D84F7C2C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'AB88014E-CB86-443A-9C7C-6EC2374B1DBA')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'AF4942F4-73AA-441A-9687-6E6CC4CEEB6A')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'BEFFB554-5CC8-4C76-9861-7B3FBB864500')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'C28EE9A8-FAAA-46F9-B8CC-ACBAB5369473')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'C9258D9A-EAFB-437A-8C9E-04FDA203B46E')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'E05607E2-E389-42F3-8033-916EF1B6A0FE')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'E5895E41-7EEE-4541-831A-4DCAE426AB7C')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'FE23E78A-FF09-4F6D-A531-7F23CD1404E0')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'1F6965F7-2F0F-4FCB-8D91-0C0ED7542520')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'548AD542-0DA5-4CBA-ADAA-7E1B6AD61690')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'7156A631-7775-49E2-8D14-B7B0CCD02AD5')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'8AAE46F0-7B8C-493D-9BBF-B07C5CBDD7C1')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', N'AFE9186D-5420-4985-871B-4DF088DC93C9')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'29BF554E-CAC6-48AC-A05E-88B55EBEF0A8')
INSERT [dbo].[permission_role] ([role_id], [permission_id]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', N'4D4C2864-7900-4570-A0F8-ABE5961C8135')
GO
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'08624617-902A-4FEB-9D5F-EDBA9519485D', N'/products/variants/{variantId}/details/{productDetailId}', CAST(N'2024-11-25T13:51:15.0533330' AS DateTime2), N'system', N'DELETE', N'Product Variant Detail', N'Delete Product Variant Detail By Variant ID and Detail ID', CAST(N'2024-11-25T13:51:15.0533330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'0A144312-7A14-48AC-8179-3728685AAF4C', N'/orders/{id}', CAST(N'2024-11-25T14:06:38.0800000' AS DateTime2), N'system', N'PUT', N'Order', N'Update Order By ID', CAST(N'2024-11-25T14:06:38.0800000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'0A96E625-2991-46A9-ADD2-4C7ACF876144', N'/roles', CAST(N'2024-11-25T13:45:20.3866670' AS DateTime2), N'system', N'PUT', N'Role', N'Update Role', CAST(N'2024-11-25T13:45:20.3866670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'0B78E831-3752-4547-A0F7-C2CE2DD8891B', N'/products/{id}', CAST(N'2024-11-25T13:46:39.7100000' AS DateTime2), N'system', N'PUT', N'Product', N'Update Product By ID', CAST(N'2024-11-25T13:46:39.7100000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'0DDD389F-BAAF-42A7-80A1-9A3775A1C252', N'/email', CAST(N'2024-11-25T14:17:45.2333330' AS DateTime2), N'system', N'GET', N'Email', N'Get Email Details', CAST(N'2024-11-25T14:17:45.2333330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'0F371E03-E1A9-4ED7-AB9D-7FEC4B6C585A', N'/system-users/{id}', CAST(N'2024-11-25T13:42:33.4600000' AS DateTime2), N'system', N'PUT', N'System User', N'Update System User By ID', CAST(N'2024-11-25T13:42:33.4600000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'0FC4465F-2F9E-4F64-B3A2-A3AB90B5D194', N'/products/{id}', CAST(N'2024-11-25T13:46:39.7100000' AS DateTime2), N'system', N'DELETE', N'Product', N'Delete Product By ID', CAST(N'2024-11-25T13:46:39.7100000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'1004A870-B62E-4DD2-A54F-712B7552359D', N'/products/image/{productId}', CAST(N'2024-11-26T19:36:43.8833330' AS DateTime2), N'system', N'GET', N'Product Image', N'Get Product Images', CAST(N'2024-11-26T19:36:43.8833330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'103B88A8-6EAA-4FB9-9DD1-B7D44C25DE25', N'/memories/ids', CAST(N'2024-11-26T19:36:43.8833330' AS DateTime2), N'system', N'GET', N'Memory', N'Get Memory IDs', CAST(N'2024-11-26T19:36:43.8833330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'14502C57-6B5A-4611-B08D-EBA91D34621A', N'/products/{productId}/variants', CAST(N'2024-11-25T13:47:53.0266670' AS DateTime2), N'system', N'POST', N'Product Variant', N'Create Product Variant By Product ID', CAST(N'2024-11-25T13:47:53.0266670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'156CE29E-214C-42B8-A7CC-1768FDB27BBF', N'/trademarks', CAST(N'2024-11-25T13:38:14.7600000' AS DateTime2), N'system', N'GET', N'Trademark', N'Get All Trademarks', CAST(N'2024-11-25T13:38:14.7600000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'15FF5087-9B95-4BCC-943C-B5EA2F4E4FD8', N'/products/variants/{variantId}/details/getProductVariantDetailByProductVariantAndColorAndMemory', CAST(N'2024-11-26T19:36:43.8833330' AS DateTime2), N'system', N'GET', N'Product Variant Detail', N'Get Product Variant Detail By Color and Memory', CAST(N'2024-11-26T19:36:43.8833330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'16252B7D-1E01-4CD5-86EA-86397C660150', N'/usageCategories', CAST(N'2024-11-25T13:36:20.7866670' AS DateTime2), N'system', N'GET', N'Usage Category', N'Get All Usage Categories', CAST(N'2024-11-25T13:36:20.7866670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'1A234409-3FA9-4765-A5B4-A3878433108F', N'/products/variants/{variantId}/details/{productDetailId}', CAST(N'2024-11-25T13:51:15.0533330' AS DateTime2), N'system', N'GET', N'Product Variant Detail', N'Get Product Variant Detail By Variant ID and Detail ID', CAST(N'2024-11-25T13:51:15.0533330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'1AFDDEC4-C759-4798-9B5B-FA612D076E82', N'/products/variants/details/getProductDetailsByIds', NULL, N'Admin', N'GET', N'Product', N'Get Product Details by IDs', NULL, N'Admin')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'1BE62AD0-C014-4A0F-8B6C-B3224C47509B', N'/colors/ids', CAST(N'2024-11-26T19:36:43.8833330' AS DateTime2), N'system', N'GET', N'Color', N'Get Color IDs', CAST(N'2024-11-26T19:36:43.8833330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'1C3D609E-435A-480A-B427-573F0BBB0142', N'/customers/{id}', CAST(N'2024-11-25T14:07:57.7566670' AS DateTime2), N'system', N'PUT', N'Customer', N'Update Customer By ID', CAST(N'2024-11-25T14:07:57.7566670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'1E5F8877-31EA-4BDD-99DF-2B8F5CFFE7AF', N'/products/{productId}/variants/all', CAST(N'2024-11-25T13:47:53.0266670' AS DateTime2), N'system', N'GET', N'Product Variant', N'Get All Product Variants By Product ID', CAST(N'2024-11-25T13:47:53.0266670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'1E5FC3B6-9D59-4E16-9018-9094C12EF05C', N'/system-users/status/{status}', CAST(N'2024-11-25T13:42:33.4600000' AS DateTime2), N'system', N'GET', N'System User', N'Get System Users By Status', CAST(N'2024-11-25T13:42:33.4600000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'1F6965F7-2F0F-4FCB-8D91-0C0ED7542520', N'/order-details/order/{orderId}', CAST(N'2024-11-25T14:12:55.6066670' AS DateTime2), N'system', N'GET', N'Order Detail', N'Get Order Details By Order ID', CAST(N'2024-11-25T14:12:55.6066670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'2030CCB2-7CD8-4D63-ADEF-E226CF092A9A', N'/product-feedbacks/{id}', CAST(N'2024-11-25T14:01:50.9766670' AS DateTime2), N'system', N'DELETE', N'Product Feedback', N'Delete Product Feedback By ID', CAST(N'2024-11-25T14:01:50.9766670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'20A844BD-71AE-471A-AEC1-D3FC4D1CD12C', N'/trademarks/name/{name}', CAST(N'2024-11-25T13:38:14.7600000' AS DateTime2), N'system', N'GET', N'Trademark', N'Get Trademark By Name', CAST(N'2024-11-25T13:38:14.7600000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'217FBB33-6CF5-440A-A8B9-B3BFA0E86F72', N'/products/{productId}/variants/{id}', CAST(N'2024-11-25T13:47:53.0266670' AS DateTime2), N'system', N'GET', N'Product Variant', N'Get Product Variant By Product ID and Variant ID', CAST(N'2024-11-25T13:47:53.0266670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'2956124E-2B9B-47E2-A815-B75C0964A2BC', N'/system-users', CAST(N'2024-11-25T13:42:33.4600000' AS DateTime2), N'system', N'GET', N'System User', N'Get All System Users', CAST(N'2024-11-25T13:42:33.4600000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'29BF554E-CAC6-48AC-A05E-88B55EBEF0A8', N'/order-details/{id}', CAST(N'2024-12-01T09:13:45.6700000' AS DateTime2), N'system', N'PUT', N'Order Detail', N'Update Order Detail By Id', CAST(N'2024-12-01T09:13:45.6700000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'2F62EEA8-95FC-4655-A19D-D6FA10296514', N'/products/image/{variantDetail}', CAST(N'2024-11-26T19:36:43.8833330' AS DateTime2), N'system', N'POST', N'Product Image', N'Add Product Image', CAST(N'2024-11-26T19:36:43.8833330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'3137A55F-7008-4D2A-816F-6758558523FC', N'/trademarks', CAST(N'2024-11-25T13:38:14.7600000' AS DateTime2), N'system', N'PUT', N'Trademark', N'Update Trademark', CAST(N'2024-11-25T13:38:14.7600000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'32709C83-D5F8-437A-A208-4559646F7E42', N'/roles/email/{email}', CAST(N'2024-11-26T19:59:42.5033330' AS DateTime2), N'system', N'GET', N'Role', N'Get Role By Email', CAST(N'2024-11-26T19:59:42.5033330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'32DD3EED-FDB4-40C9-AC32-B3AF639E4664', N'/api/accounts/exists-by-email/{email}', CAST(N'2024-11-26T19:36:43.8833330' AS DateTime2), N'system', N'GET', N'Account', N'Check Account Existence By Email', CAST(N'2024-11-26T19:36:43.8833330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'3363672E-0604-47D6-A7E6-9AB1BC139DAA', N'/api/accounts/email/{email}', CAST(N'2024-11-25T14:11:18.2366670' AS DateTime2), N'system', N'GET', N'Account', N'Get Account By Email', CAST(N'2024-11-25T14:11:18.2366670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'356850B1-762E-4FB6-B7E0-A07C8A28DD63', N'/products/{id}', CAST(N'2024-11-25T13:46:39.7100000' AS DateTime2), N'system', N'GET', N'Product', N'Get Product By ID', CAST(N'2024-11-25T13:46:39.7100000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'384420DC-4BC3-4531-977E-6644EF2EE86C', N'/api/accounts/auth/account', CAST(N'2024-11-25T14:11:18.2366670' AS DateTime2), N'system', N'GET', N'Account', N'Get Authenticated Account', CAST(N'2024-11-25T14:11:18.2366670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'39013242-78B2-4DD0-8F72-328CDCA88BA4', N'/attributes/value/{id}', CAST(N'2024-11-25T14:09:12.3300000' AS DateTime2), N'system', N'GET', N'Attribute', N'Get Attribute Value By ID', CAST(N'2024-11-25T14:09:12.3300000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'39C7F908-86B3-41B7-91D7-50004878473C', N'/product-feedbacks', CAST(N'2024-11-25T14:01:50.9766670' AS DateTime2), N'system', N'GET', N'Product Feedback', N'Get All Product Feedbacks', CAST(N'2024-11-25T14:01:50.9766670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'3E1113DC-5F39-422B-8676-9CE2737FB4CA', N'/product-feedbacks/product-variantv2/{productVariantId}', CAST(N'2024-11-30T11:58:13.3966670' AS DateTime2), N'system', N'GET', N'Product Feedback', N'Get Product Feedback by Product Variant V2', CAST(N'2024-11-30T11:58:13.3966670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'3E8E7881-5B4C-44CB-914E-64F08B73A2CD', N'/api/accounts/auth/register', CAST(N'2024-11-25T14:11:18.2366670' AS DateTime2), N'system', N'POST', N'Account', N'Register Account', CAST(N'2024-11-25T14:11:18.2366670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'41F3A36A-3F76-48BE-8A1F-29B3386CA880', N'/permissions', CAST(N'2024-11-25T14:03:15.9466670' AS DateTime2), N'system', N'POST', N'Permission', N'Create Permission', CAST(N'2024-11-25T14:03:15.9466670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'4635EDBE-7345-4DF5-969E-6300698DDE86', N'/trademarks/id/{id}', CAST(N'2024-11-25T13:38:14.7600000' AS DateTime2), N'system', N'GET', N'Trademark', N'Get Trademark By ID', CAST(N'2024-11-25T13:38:14.7600000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'46402533-EE8F-4FD9-AE6D-55BD838ACF34', N'/api/accounts/auth/refresh-token', CAST(N'2024-11-25T14:11:18.2366670' AS DateTime2), N'system', N'POST', N'Account', N'Refresh Token', CAST(N'2024-11-25T14:11:18.2366670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'465CEDBD-A373-4694-934A-8C3B7C6B2D23', N'/products/{productId}/variants/productVariantDetail/{id}', CAST(N'2024-11-25T13:47:53.0266670' AS DateTime2), N'system', N'GET', N'Product Variant', N'Get Product Variant Detail By Product Variant ID', CAST(N'2024-11-25T13:47:53.0266670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'479EF456-9225-4B25-83EC-2FBCD1461BC4', N'/product-feedbacks/v2', CAST(N'2024-11-30T11:58:13.3966670' AS DateTime2), N'system', N'POST', N'Product Feedback', N'Create Product Feedback V2', CAST(N'2024-11-30T11:58:13.3966670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'4D4C2864-7900-4570-A0F8-ABE5961C8135', N'/order-details/order/{orderId}/product-variant-detail/{productVariantDetailId}', CAST(N'2024-12-01T09:15:39.7700000' AS DateTime2), N'system', N'GET', N'Order Detail', N'Get product variant detail by order id', CAST(N'2024-12-01T09:15:39.7700000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'4DCFDE53-DE79-4781-9943-1AB9609DEA20', N'/roles', CAST(N'2024-11-25T13:45:20.3866670' AS DateTime2), N'system', N'GET', N'Role', N'Get All Roles', CAST(N'2024-11-25T13:45:20.3866670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'51EC51FB-3D94-4BA2-AE95-3F7C99F965FE', N'/payment/vn-pay-callback', CAST(N'2024-11-25T14:15:21.1166670' AS DateTime2), N'system', N'GET', N'Payment', N'VNPay Payment Callback', CAST(N'2024-11-25T14:15:21.1166670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'523C6348-ECDA-4A19-8A64-B990AE3FCCF1', N'/attributes/attributevalue/{name}', CAST(N'2024-11-25T14:09:12.3333330' AS DateTime2), N'system', N'GET', N'Attribute', N'Get Attribute By Name', CAST(N'2024-11-25T14:09:12.3333330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'548AD542-0DA5-4CBA-ADAA-7E1B6AD61690', N'/orders/v2', CAST(N'2024-11-29T12:01:22.5533330' AS DateTime2), N'system', N'POST', N'Order', N'Create order by cus', CAST(N'2024-11-29T12:01:22.5533330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'55C26BEA-D7AF-4961-B1F9-C09C06129B2A', N'/product-feedbacks/{id}', CAST(N'2024-11-25T14:01:50.9766670' AS DateTime2), N'system', N'PUT', N'Product Feedback', N'Update Product Feedback By ID', CAST(N'2024-11-25T14:01:50.9766670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'563569EE-3418-40A2-B3BB-491F67640BE2', N'/product-feedbacks/{productFeedbackId}/img-feedbacks', CAST(N'2024-11-25T13:54:22.3666670' AS DateTime2), N'system', N'POST', N'Img Product Feedback', N'Add Image to Product Feedback', CAST(N'2024-11-25T13:54:22.3666670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'58D50708-C82B-4D9E-A04A-B4328EEC5000', N'/attributes/productvariant/{provariant}', CAST(N'2024-11-25T14:09:12.3300000' AS DateTime2), N'system', N'PUT', N'Attribute', N'Update Attribute for Product Variant', CAST(N'2024-11-25T14:09:12.3300000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'59006012-6320-466d-ab41-266cd8e7639f', N'/memories', CAST(N'2025-04-26T02:45:19.8096350' AS DateTime2), NULL, N'POST', N'Memories', N'create Memory', CAST(N'2025-04-26T02:45:19.8096650' AS DateTime2), NULL)
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'5F5C4574-ACCD-411F-AEAB-210B97B302B8', N'/products/variants/{variantId}/details/getProductDetailsByIds', NULL, N'Admin', N'GET', N'Product', N'Get Product Details by Variant ID', NULL, N'Admin')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'60B00935-B584-4CB2-ABB4-2EDC5CAE1BF5', N'/attributes', CAST(N'2024-11-25T14:09:12.3300000' AS DateTime2), N'system', N'GET', N'Attribute', N'Get All Attributes', CAST(N'2024-11-25T14:09:12.3300000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'6359f424-577c-421e-9127-6876dd530d1a', N'/memories/{id}', CAST(N'2025-04-26T03:31:19.0799090' AS DateTime2), NULL, N'PUT', N'Memories', N'update Memory', CAST(N'2025-04-26T03:31:19.0799520' AS DateTime2), NULL)
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'635E1B34-4006-4077-9A43-DA02F542E8AD', N'/system-users/email/{email}', CAST(N'2024-11-25T13:42:33.4600000' AS DateTime2), N'system', N'GET', N'System User', N'Get System User By Email', CAST(N'2024-11-25T13:42:33.4600000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'64E0D429-D2C3-4245-BA0B-5BF1874BF246', N'/products', CAST(N'2024-11-25T13:46:39.7100000' AS DateTime2), N'system', N'GET', N'Product', N'Get All Products', CAST(N'2024-11-25T13:46:39.7100000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'67F7B143-1680-410B-B9B0-CD517D6FDE12', N'/products', CAST(N'2024-11-25T13:46:39.7100000' AS DateTime2), N'system', N'POST', N'Product', N'Create Product', CAST(N'2024-11-25T13:46:39.7100000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'691AC998-90B4-47BF-AF6C-2F6B1EF418FD', N'/api/accounts/auth/create-system-user-account', CAST(N'2024-11-25T14:11:18.2366670' AS DateTime2), N'system', N'POST', N'Account', N'Create System User Account', CAST(N'2024-11-25T14:11:18.2366670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'6AA63970-1F15-4F42-8075-A3F706D9EB69', N'/product-feedbacks/{productFeedbackId}/img-feedbacks', CAST(N'2024-11-26T19:36:43.8833330' AS DateTime2), N'system', N'GET', N'Product Feedback Image', N'Get Feedback Images', CAST(N'2024-11-26T19:36:43.8833330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'6BCCCD9B-0DA7-434A-A6EF-C1AF537BA87B', N'/products/variants/details/{productDetailId}', CAST(N'2024-11-25T13:51:15.0533330' AS DateTime2), N'system', N'GET', N'Product Variant Detail', N'Get Product Variant Detail By Detail ID', CAST(N'2024-11-25T13:51:15.0533330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'6C2A64A3-D03A-43A4-A57D-69927C5A7EDE', N'/products/variants/{variantId}/details/{productDetailId}', CAST(N'2024-11-25T13:51:15.0533330' AS DateTime2), N'system', N'PUT', N'Product Variant Detail', N'Update Product Variant Detail By Variant ID and Detail ID', CAST(N'2024-11-25T13:51:15.0533330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'6D77C666-AA32-4453-B1DF-CF5FA0ED4D76', N'/colors/{id}', CAST(N'2024-11-25T14:18:16.3633330' AS DateTime2), N'system', N'GET', N'Color', N'Get Color By ID', CAST(N'2024-11-25T14:18:16.3633330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'6DD32F90-049A-4AD6-A211-83EE3FF9E895', N'/customers', CAST(N'2024-11-25T14:07:57.7566670' AS DateTime2), N'system', N'POST', N'Customer', N'Create Customer', CAST(N'2024-11-25T14:07:57.7566670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'7156A631-7775-49E2-8D14-B7B0CCD02AD5', N'/orders/{id}', CAST(N'2024-11-25T14:06:38.0800000' AS DateTime2), N'system', N'GET', N'Order', N'Get Order By ID', CAST(N'2024-11-25T14:06:38.0800000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'72D2D532-244A-4389-8238-850584D3247B', N'/products/variants/details/{productDetailId}', CAST(N'2024-11-25T13:51:15.0533330' AS DateTime2), N'system', N'DELETE', N'Product Variant Detail', N'Delete Product Variant Detail By Detail ID', CAST(N'2024-11-25T13:51:15.0533330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'732D54ED-85E2-42A8-BC19-F49893216C9C', N'/api/accounts/{id}', CAST(N'2024-11-25T14:11:18.2366670' AS DateTime2), N'system', N'DELETE', N'Account', N'Delete Account By ID', CAST(N'2024-11-25T14:11:18.2366670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'733928F5-D917-41B9-A02B-10B9E10C76BC', N'/customers/{id}', CAST(N'2024-11-25T14:07:57.7566670' AS DateTime2), N'system', N'DELETE', N'Customer', N'Delete Customer By ID', CAST(N'2024-11-25T14:07:57.7566670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'765c504b-4730-4827-86ab-027495660862', N'/memories/{id}', CAST(N'2025-04-26T03:00:03.7990920' AS DateTime2), NULL, N'DELETE', N'Memories', N'delete Memory', CAST(N'2025-04-26T03:00:03.7991060' AS DateTime2), NULL)
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'787774E3-94DB-4CFF-8CE7-033EE627F1F0', N'/system-users', CAST(N'2024-11-25T13:42:33.4600000' AS DateTime2), N'system', N'POST', N'System User', N'Create System User', CAST(N'2024-11-25T13:42:33.4600000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'794C0169-2080-4EFD-AFD6-04D53505AFC4', N'/products/variants/details/getProductVariantDetailByProductVariantAndColorAndMemory', CAST(N'2024-11-26T19:36:43.8833330' AS DateTime2), N'system', N'GET', N'Product Variant Detail', N'Get Product Variant Detail By Color and Memory', CAST(N'2024-11-26T19:36:43.8833330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'7AB569D0-AAEF-422B-B52C-B8CFB0CBB2DB', N'/products/variants', CAST(N'2024-11-25T13:47:53.0266670' AS DateTime2), N'system', N'POST', N'Product Variant', N'Create Product Variant', CAST(N'2024-11-25T13:47:53.0266670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'7DA76E28-EEAD-4B56-A847-F7610D92A7D5', N'/products/variants/details', CAST(N'2024-11-25T13:51:15.0533330' AS DateTime2), N'system', N'POST', N'Product Variant Detail', N'Create Product Variant Detail', CAST(N'2024-11-25T13:51:15.0533330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'7DC0F873-7BE7-4F25-8CDE-0E0A999B9D26', N'/roles/{id}', CAST(N'2024-11-25T13:45:20.3866670' AS DateTime2), N'system', N'DELETE', N'Role', N'Delete Role By ID', CAST(N'2024-11-25T13:45:20.3866670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'85894C9B-D1A5-436F-A131-D9D3AE6569D5', N'/colors', CAST(N'2024-11-25T14:18:16.3633330' AS DateTime2), N'system', N'GET', N'Color', N'Get All Colors', CAST(N'2024-11-25T14:18:16.3633330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'86A39945-85BB-4E6E-B7DA-164E0CEC43D2', N'/permissions/{id}', CAST(N'2024-11-25T14:03:15.9466670' AS DateTime2), N'system', N'DELETE', N'Permission', N'Delete Permission By ID', CAST(N'2024-11-25T14:03:15.9466670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'86CEAA1A-DA55-4D90-980C-F4B393EF1C62', N'/products/image/{productId}', CAST(N'2024-11-26T19:36:43.8833330' AS DateTime2), N'system', N'DELETE', N'Product Image', N'Delete Product Images', CAST(N'2024-11-26T19:36:43.8833330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'8735E083-575D-4AF5-94ED-AE4E7AFD2E17', N'/product-feedbacks/customer/{customerId}', CAST(N'2024-11-25T14:01:50.9766670' AS DateTime2), N'system', N'GET', N'Product Feedback', N'Get Feedbacks By Customer ID', CAST(N'2024-11-25T14:01:50.9766670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'8AAE46F0-7B8C-493D-9BBF-B07C5CBDD7C1', N'/orders/customer/{customerId}', CAST(N'2024-11-25T13:51:15.0533330' AS DateTime2), N'system', N'GET', N'Order', N'Get order by custommer id', CAST(N'2024-11-25T13:51:15.0533330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'8C5064B9-9EAF-466C-8543-91B0FA4CCE08', N'/memories', CAST(N'2024-11-25T14:17:00.4733330' AS DateTime2), N'system', N'GET', N'Memory', N'Get All Memories', CAST(N'2024-11-25T14:17:00.4733330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'8CEF3587-A9F2-46BC-A31A-A7DABBD61F9B', N'/file', CAST(N'2024-11-25T14:14:07.4033330' AS DateTime2), N'system', N'POST', N'File', N'Upload Single File', CAST(N'2024-11-25T14:14:07.4033330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'8E25049E-6952-46DF-8068-4F4C46A0AE5E', N'/products/variants/all', CAST(N'2024-11-25T13:47:53.0266670' AS DateTime2), N'system', N'GET', N'Product Variant', N'Get All Product Variants', CAST(N'2024-11-25T13:47:53.0266670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'8FB87F83-6336-477D-A088-C085327954A7', N'/products/variants/{id}', CAST(N'2024-11-25T13:47:53.0266670' AS DateTime2), N'system', N'PUT', N'Product Variant', N'Update Product Variant By ID', CAST(N'2024-11-25T13:47:53.0266670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'90B404BB-0F42-4D8F-9234-7B41D96AB679', N'/order-details', CAST(N'2024-11-25T14:12:55.6066670' AS DateTime2), N'system', N'POST', N'Order Detail', N'Create Order Detail', CAST(N'2024-11-25T14:12:55.6066670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'938BAFBF-22DF-40A0-8ACC-E2F467E96F90', N'/customers/profile/email/{email}', NULL, N'Admin', N'GET', N'Customer', N'Get profile', NULL, N'Admin')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'98AE81B2-1575-498A-91C4-16B1D84F7C2C', N'/products/variants/productVariantDetail/{id}', CAST(N'2024-11-25T13:47:53.0266670' AS DateTime2), N'system', N'GET', N'Product Variant', N'Get Product Variant Detail By Variant ID', CAST(N'2024-11-25T13:47:53.0266670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'991C4FBB-67EE-42BA-9152-42E36D7C0AF0', N'/products/{productId}/variants/{id}', CAST(N'2024-11-25T13:47:53.0266670' AS DateTime2), N'system', N'DELETE', N'Product Variant', N'Delete Product Variant By Product ID and Variant ID', CAST(N'2024-11-25T13:47:53.0266670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'99480005-0567-48B2-BBFB-9974F0B23C1A', N'/api/accounts/update-account-without-password', CAST(N'2024-11-25T14:11:18.2366670' AS DateTime2), N'system', N'PUT', N'Account', N'Update Account Without Password', CAST(N'2024-11-25T14:11:18.2366670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'9A5D914F-EE72-4507-8C4B-FBEA254514DF', N'/roles/{id}', CAST(N'2024-11-25T13:45:20.3866670' AS DateTime2), N'system', N'GET', N'Role', N'Get Role By ID', CAST(N'2024-11-25T13:45:20.3866670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'9C7417D6-EE2F-421C-88BC-C508CD17E970', N'/attributes/attributeByVariantId/{variantId}', CAST(N'2024-11-25T14:09:12.3300000' AS DateTime2), N'system', N'GET', N'Attribute', N'Get Attributes By Variant ID', CAST(N'2024-11-25T14:09:12.3300000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'9DDE7684-93B4-4124-9643-772F96F99190', N'/usageCategories/usageCategories/{id}', CAST(N'2024-11-25T13:36:20.7866670' AS DateTime2), N'system', N'DELETE', N'Usage Category', N'Delete Usage Category By ID', CAST(N'2024-11-25T13:36:20.7866670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'9DFD8BB9-0EDC-4CA8-B08A-7629417E6B58', N'/memories/{id}', CAST(N'2024-11-25T14:17:00.4733330' AS DateTime2), N'system', N'GET', N'Memory', N'Get Memory By ID', CAST(N'2024-11-25T14:17:00.4733330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'9FCE096A-352C-4AA4-AEE4-A635EABA8727', N'/system-users/{id}', CAST(N'2024-11-25T13:42:33.4600000' AS DateTime2), N'system', N'GET', N'System User', N'Get System User By ID', CAST(N'2024-11-25T13:42:33.4600000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'A673CF7C-805C-43AF-9158-5EEFECECC4D6', N'/attributes/atributevalue/{name}', CAST(N'2024-11-26T19:36:43.8833330' AS DateTime2), N'system', N'GET', N'Attribute', N'Get Attribute By Name', CAST(N'2024-11-26T19:36:43.8833330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'A8382C2C-4BBA-43F6-B71E-10439959E6A3', N'/usageCategories/usageCategories/{id}', CAST(N'2024-11-25T13:36:20.7866670' AS DateTime2), N'system', N'PUT', N'Usage Category', N'Update Usage Category By ID', CAST(N'2024-11-25T13:36:20.7866670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'A908715D-4EA7-4745-B43D-EAD2456ED8E9', N'/attributes/productvariant/{provariant}', CAST(N'2024-11-25T14:09:12.3300000' AS DateTime2), N'system', N'POST', N'Attribute', N'Create Attribute for Product Variant', CAST(N'2024-11-25T14:09:12.3300000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'AA2A41AE-9032-43E2-8A1F-88921F971D23', N'/customers/email/{email}', CAST(N'2024-11-25T14:07:57.7566670' AS DateTime2), N'system', N'GET', N'Customer', N'Get Customer By Email', CAST(N'2024-11-25T14:07:57.7566670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'AB6573A7-AC61-456E-BC70-4E6AA530037D', N'/api/accounts/{id}', CAST(N'2024-11-25T14:11:18.2366670' AS DateTime2), N'system', N'GET', N'Account', N'Get Account By ID', CAST(N'2024-11-25T14:11:18.2366670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'AB88014E-CB86-443A-9C7C-6EC2374B1DBA', N'/product-feedbacks/product-variant/{productVariantId}', CAST(N'2024-11-25T14:01:50.9766670' AS DateTime2), N'system', N'GET', N'Product Feedback', N'Get Feedbacks By Product Variant ID', CAST(N'2024-11-25T14:01:50.9766670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'ADC5B60D-C899-40E7-ACE2-D1E781CE1165', N'/system-users', CAST(N'2024-11-25T13:42:33.4600000' AS DateTime2), N'system', N'PUT', N'System User', N'Update All System Users', CAST(N'2024-11-25T13:42:33.4600000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'AE5E6126-252E-4CEF-B0EF-BCA71C0A5C1B', N'/products/image/delete/{imageId}', CAST(N'2024-11-26T19:36:43.8833330' AS DateTime2), N'system', N'DELETE', N'Product Image', N'Delete Product Image By ID', CAST(N'2024-11-26T19:36:43.8833330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'AF4942F4-73AA-441A-9687-6E6CC4CEEB6A', N'/products/{productId}/variants', CAST(N'2024-11-25T13:47:53.0266670' AS DateTime2), N'system', N'GET', N'Product Variant', N'Get Product Variants By Product ID', CAST(N'2024-11-25T13:47:53.0266670' AS DateTime2), N'system')
GO
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'AFE9186D-5420-4985-871B-4DF088DC93C9', N'/order-details/{id}', CAST(N'2024-11-25T14:12:55.6066670' AS DateTime2), N'system', N'GET', N'Order Detail', N'Get Order Detail By ID', CAST(N'2024-11-25T14:12:55.6066670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'b0313f8c-8dab-4d65-bb2a-f4bb91e49c46', N'/colors/{id}', CAST(N'2025-04-25T15:07:27.7987100' AS DateTime2), NULL, N'PUT', N'Color', N'update Color', CAST(N'2025-04-25T15:07:27.7989810' AS DateTime2), NULL)
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'B17F1C8E-CC26-48E8-B89F-D09D00DD9A2D', N'/system-users/all', CAST(N'2024-11-25T13:42:33.4600000' AS DateTime2), N'system', N'GET', N'System User', N'Get All System Users (Admin)', CAST(N'2024-11-25T13:42:33.4600000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'B2330DB9-14F7-46D3-8F37-BFC1EC3E3AA9', N'/products/variants/details/{productDetailId}', CAST(N'2024-11-25T13:51:15.0533330' AS DateTime2), N'system', N'PUT', N'Product Variant Detail', N'Update Product Variant Detail By Detail ID', CAST(N'2024-11-25T13:51:15.0533330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'B6BE9B4C-94DD-45ED-8AD5-20F71569B706', N'/usageCategories/usageCategories/{id}', CAST(N'2024-11-25T13:36:20.7866670' AS DateTime2), N'system', N'GET', N'Usage Category', N'Get Usage Category By ID', CAST(N'2024-11-25T13:36:20.7866670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'B8AB5460-C685-44AF-B508-3959F67B4927', N'/usageCategories', CAST(N'2024-11-25T13:36:20.7866670' AS DateTime2), N'system', N'POST', N'Usage Category', N'Create Usage Category', CAST(N'2024-11-25T13:36:20.7866670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'B92408CD-1FDB-49C1-80A4-4E5ADBAEDCE0', N'/api/accounts/auth/create-system-user', CAST(N'2024-11-25T14:11:18.2366670' AS DateTime2), N'system', N'POST', N'Account', N'Create System User', CAST(N'2024-11-25T14:11:18.2366670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'BA2C6EC0-58BC-47A4-B6F1-2F9990086091', N'/orders', CAST(N'2024-11-25T14:06:38.0800000' AS DateTime2), N'system', N'GET', N'Order', N'Get All Orders', CAST(N'2024-11-25T14:06:38.0800000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'BEFFB554-5CC8-4C76-9861-7B3FBB864500', N'/products/variants/{id}', CAST(N'2024-11-25T13:47:53.0266670' AS DateTime2), N'system', N'GET', N'Product Variant', N'Get Product Variant By ID', CAST(N'2024-11-25T13:47:53.0266670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'BF27F60E-C46E-4D3A-B203-A5EDF6FA501D', N'/attributes/value/{id}', CAST(N'2024-11-25T14:09:12.3300000' AS DateTime2), N'system', N'DELETE', N'Attribute', N'Delete Attribute Value By ID', CAST(N'2024-11-25T14:09:12.3300000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'C1E4A346-B003-414A-BC31-3E3AAB30FF2C', N'/products/variants/{id}', CAST(N'2024-11-25T13:47:53.0266670' AS DateTime2), N'system', N'DELETE', N'Product Variant', N'Delete Product Variant By ID', CAST(N'2024-11-25T13:47:53.0266670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'C20074A4-2823-4C22-9B6C-E899B58420D3', N'/trademarks/{id}', CAST(N'2024-11-25T13:38:14.7600000' AS DateTime2), N'system', N'DELETE', N'Trademark', N'Delete Trademark By ID', CAST(N'2024-11-25T13:38:14.7600000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'C28EE9A8-FAAA-46F9-B8CC-ACBAB5369473', N'/products/variants/details/filter', CAST(N'2024-11-25T13:51:15.0533330' AS DateTime2), N'system', N'GET', N'Product Variant Detail', N'Filter Product Variant Details', CAST(N'2024-11-25T13:51:15.0533330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'C4FE0A0B-E6C0-4028-8796-F0EF2BD4F79F', N'/permissions', CAST(N'2024-11-25T14:03:15.9466670' AS DateTime2), N'system', N'GET', N'Permission', N'Get All Permissions', CAST(N'2024-11-25T14:03:15.9466670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'C54EBE29-75EB-4C9B-83EE-66E23363F0E7', N'/trademarks', CAST(N'2024-11-25T13:38:14.7600000' AS DateTime2), N'system', N'POST', N'Trademark', N'Create Trademark', CAST(N'2024-11-25T13:38:14.7600000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'C73504BF-EBC1-4667-8B27-201BCE7F1D6B', N'/system-users/{id}', CAST(N'2024-11-25T13:42:33.4600000' AS DateTime2), N'system', N'DELETE', N'System User', N'Delete System User By ID', CAST(N'2024-11-25T13:42:33.4600000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'C75DBF6F-23A0-468A-B51E-4C8F88994A2A', N'/api/accounts', CAST(N'2024-11-25T14:11:18.2366670' AS DateTime2), N'system', N'PUT', N'Account', N'Update Account', CAST(N'2024-11-25T14:11:18.2366670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'C7ADCE32-A20E-4C2A-BDEE-58ECC4610A2B', N'/products/{productId}/variants/{id}', CAST(N'2024-11-25T13:47:53.0266670' AS DateTime2), N'system', N'PUT', N'Product Variant', N'Update Product Variant By Product ID and Variant ID', CAST(N'2024-11-25T13:47:53.0266670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'C9258D9A-EAFB-437A-8C9E-04FDA203B46E', N'/products/variants/{variantId}/details', CAST(N'2024-11-25T13:51:15.0533330' AS DateTime2), N'system', N'GET', N'Product Variant Detail', N'Get All Details For A Variant', CAST(N'2024-11-25T13:51:15.0533330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'C9C225AD-C12A-4C34-BD2A-1BEA0D095B98', N'/customers', CAST(N'2024-11-25T14:07:57.7566670' AS DateTime2), N'system', N'GET', N'Customer', N'Get All Customers', CAST(N'2024-11-25T14:07:57.7566670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'D184E803-DD2A-4FB3-81A9-0E4C1CE12F9E', N'/product-feedbacks', CAST(N'2024-11-25T14:01:50.9766670' AS DateTime2), N'system', N'POST', N'Product Feedback', N'Create Product Feedback', CAST(N'2024-11-25T14:01:50.9766670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'D386A437-0314-4E04-B77E-F53E1DE3F00E', N'/product-feedbacks/{productFeedbackId}/img-feedbacks/{id}', CAST(N'2024-11-25T13:54:22.3666670' AS DateTime2), N'system', N'DELETE', N'Img Product Feedback', N'Delete Image from Product Feedback', CAST(N'2024-11-25T13:54:22.3666670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'D3B7074E-A6B4-4980-BD39-8E8DC12B7A72', N'/system-users', CAST(N'2024-11-25T13:42:33.4600000' AS DateTime2), N'system', N'DELETE', N'System User', N'Delete All System Users', CAST(N'2024-11-25T13:42:33.4600000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'D3FABE3A-C2D5-4686-90F8-5A00C3627876', N'/payment/vn-pay', CAST(N'2024-11-25T14:15:21.1166670' AS DateTime2), N'system', N'GET', N'Payment', N'Initiate VNPay Payment', CAST(N'2024-11-25T14:15:21.1166670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'd4cf4952-2804-47ed-b6f3-051b3189cee5', N'/colors/{id}', CAST(N'2025-04-25T07:07:24.3594380' AS DateTime2), NULL, N'DELETE', N'Color', N'Delete Color By Id', CAST(N'2025-04-25T07:07:24.3594570' AS DateTime2), NULL)
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'D84F073C-419B-4B90-B300-A644CACEB71C', N'/permissions/{id}', CAST(N'2024-11-25T14:03:15.9466670' AS DateTime2), N'system', N'PUT', N'Permission', N'Update Permission By ID', CAST(N'2024-11-25T14:03:15.9466670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'D8C05581-5242-4C59-9556-97984FD3EA9D', N'/api/accounts/auth/logout', CAST(N'2024-11-25T14:11:18.2366670' AS DateTime2), N'system', N'POST', N'Account', N'Logout', CAST(N'2024-11-25T14:11:18.2366670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'D93714E5-B43B-485A-849C-1A245FC385D1', N'/products/variants/{variantId}/details', CAST(N'2024-11-25T13:51:15.0533330' AS DateTime2), N'system', N'POST', N'Product Variant Detail', N'Create Details For A Variant', CAST(N'2024-11-25T13:51:15.0533330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'DB5A6DBE-C1EB-4000-8527-8DA5626A38F4', N'/attributes/attributes/{id}', CAST(N'2024-11-25T14:09:12.3300000' AS DateTime2), N'system', N'PUT', N'Attribute', N'Update Attribute By ID', CAST(N'2024-11-25T14:09:12.3300000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'DECC7FDB-E906-461B-82EC-4E20B1CCC257', N'/api/accounts/auth/create-customer-account', CAST(N'2024-11-25T14:11:18.2366670' AS DateTime2), N'system', N'POST', N'Account', N'Create Customer Account', CAST(N'2024-11-25T14:11:18.2366670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'E05607E2-E389-42F3-8033-916EF1B6A0FE', N'/products/variants', CAST(N'2024-11-25T13:47:53.0266670' AS DateTime2), N'system', N'GET', N'Product Variant', N'Get All Product Variants', CAST(N'2024-11-25T13:47:53.0266670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'E384055C-997D-4834-B8B9-EE25EF95796C', N'/customers/{id}', CAST(N'2024-11-25T14:07:57.7566670' AS DateTime2), N'system', N'GET', N'Customer', N'Get Customer By ID', CAST(N'2024-11-25T14:07:57.7566670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'E3E2309F-F946-4647-AD02-4FBE31B0DD19', N'/product-feedbacks/{productFeedbackId}/img-feedbacks/{id}', CAST(N'2024-11-25T13:54:22.3666670' AS DateTime2), N'system', N'PUT', N'Img Product Feedback', N'Update Image for Product Feedback', CAST(N'2024-11-25T13:54:22.3666670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'E4D33EDA-E42C-478C-920F-0A4CD3E3432B', N'/roles', CAST(N'2024-11-25T13:45:20.3866670' AS DateTime2), N'system', N'POST', N'Role', N'Create Role', CAST(N'2024-11-25T13:45:20.3866670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'E5895E41-7EEE-4541-831A-4DCAE426AB7C', N'/products/variants/{variantId}/details/filter', CAST(N'2024-11-25T13:51:15.0533330' AS DateTime2), N'system', N'GET', N'Product Variant Detail', N'Filter Product Variant Details By Variant ID', CAST(N'2024-11-25T13:51:15.0533330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'E81D4EFF-ADE4-4AA9-A468-B28409C300D2', N'/attributes/attributes/{id}', CAST(N'2024-11-25T14:09:12.3300000' AS DateTime2), N'system', N'DELETE', N'Attribute', N'Delete Attribute By ID', CAST(N'2024-11-25T14:09:12.3300000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'EB57E42B-F8AE-45B6-B162-43E353700CE0', N'/files', CAST(N'2024-11-25T14:14:07.4033330' AS DateTime2), N'system', N'POST', N'File', N'Upload Multiple Files', CAST(N'2024-11-25T14:14:07.4033330' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'ebdac04b-d92a-4d90-a075-47c4ae97e4fa', N'/colors', CAST(N'2025-04-25T07:16:36.2505910' AS DateTime2), NULL, N'POST', N'Color', N'Create Color', CAST(N'2025-04-25T07:16:36.2506130' AS DateTime2), NULL)
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'F32146EA-2B99-4590-9B4A-65ACFC6B3F8D', N'/roles/all', CAST(N'2024-11-25T13:45:20.3866670' AS DateTime2), N'system', N'GET', N'Role', N'Get All Roles (Admin Only)', CAST(N'2024-11-25T13:45:20.3866670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'F8885E65-72A3-461F-B782-6DE867A712B0', N'/api/accounts/auth/login', CAST(N'2024-11-25T14:11:18.2366670' AS DateTime2), N'system', N'POST', N'Account', N'Login', CAST(N'2024-11-25T14:11:18.2366670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'FB7516AF-38ED-4076-921F-641461C04366', N'/attributes/attributes/{id}', CAST(N'2024-11-25T14:09:12.3300000' AS DateTime2), N'system', N'GET', N'Attribute', N'Get Attribute By ID', CAST(N'2024-11-25T14:09:12.3300000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'FBC0DEF1-BA34-4F1A-B19F-847EE8355351', N'/attributes', CAST(N'2024-11-25T14:09:12.3300000' AS DateTime2), N'system', N'POST', N'Attribute', N'Create Attribute', CAST(N'2024-11-25T14:09:12.3300000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'FD0ACDFD-D72E-40B9-B208-B03205B00024', N'/api/accounts', CAST(N'2024-11-25T14:11:18.2366670' AS DateTime2), N'system', N'GET', N'Account', N'Get All Accounts', CAST(N'2024-11-25T14:11:18.2366670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'FD8D4BEA-6EEE-46FB-9CB7-9315A553CAD3', N'/orders', CAST(N'2024-11-25T14:06:38.0800000' AS DateTime2), N'system', N'POST', N'Order', N'Create Order', CAST(N'2024-11-25T14:06:38.0800000' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'FDDCAFF0-7702-4869-96F1-E2084D524DEE', N'/customers/profile/{id}', CAST(N'2024-11-29T13:54:23.4866670' AS DateTime2), N'system', N'PUT', N'Customer', N'Update profile', CAST(N'2024-11-29T13:54:23.4866670' AS DateTime2), N'system')
INSERT [dbo].[permissions] ([id], [api_path], [created_at], [created_by], [method], [module], [name], [updated_at], [updated_by]) VALUES (N'FE23E78A-FF09-4F6D-A531-7F23CD1404E0', N'/products/variants/details', CAST(N'2024-11-25T13:51:15.0533330' AS DateTime2), N'system', N'GET', N'Product Variant Detail', N'Get All Product Variant Details', CAST(N'2024-11-25T13:51:15.0533330' AS DateTime2), N'system')
GO
INSERT [dbo].[roles] ([id], [active], [created_at], [description], [name], [updated_at]) VALUES (N'67e7530a-81c5-47bf-b2ae-23ea5a2dc20e', 1, CAST(N'2024-08-18T15:00:00.0000000' AS DateTime2), N'Role for Employee', N'Employee', CAST(N'2024-08-18T15:45:00.0000000' AS DateTime2))
INSERT [dbo].[roles] ([id], [active], [created_at], [description], [name], [updated_at]) VALUES (N'ca01f704-b5fc-49d0-8370-a799f44fe28f', 1, CAST(N'2024-07-25T10:40:00.0000000' AS DateTime2), N'Role for Customer', N'Customer', CAST(N'2024-07-25T11:20:00.0000000' AS DateTime2))
INSERT [dbo].[roles] ([id], [active], [created_at], [description], [name], [updated_at]) VALUES (N'd176139a-dbb0-42e3-9ede-fb237028f740', 1, CAST(N'2023-01-15T08:30:00.0000000' AS DateTime2), N'Role for Admin', N'Admin', CAST(N'2023-01-15T09:00:00.0000000' AS DateTime2))
GO
INSERT [dbo].[system_users] ([id], [address], [avatar], [created_at], [gender], [level], [name], [phone], [system_user_status], [updated_at], [account_id]) VALUES (N'046a91e9-dfc3-4715-934d-53c427e1f992', N'123update', NULL, CAST(N'2024-11-27T21:51:17.0361640' AS DateTime2), N'FEMALE', N'MANAGER', N'SamSungGLX1', N'0123456789', N'INACTIVE', CAST(N'2024-11-27T21:51:17.0361640' AS DateTime2), N'69ae1c22-01b5-4947-a090-5b7750d0682f')
INSERT [dbo].[system_users] ([id], [address], [avatar], [created_at], [gender], [level], [name], [phone], [system_user_status], [updated_at], [account_id]) VALUES (N'07620f9b-bd00-40d1-9cd5-d09dc4f19ebd', N'Go Vap', N'undraw_profile.svg', CAST(N'2024-11-20T20:38:49.8799060' AS DateTime2), N'MALE', N'STAFF', N'Thu Tram', N'0158837298', N'ACTIVE', CAST(N'2024-11-20T20:38:49.8809010' AS DateTime2), N'327d6667-c981-411b-b4ba-0aa5b07a1d0c')
INSERT [dbo].[system_users] ([id], [address], [avatar], [created_at], [gender], [level], [name], [phone], [system_user_status], [updated_at], [account_id]) VALUES (N'104b6e20-56ff-4f63-83ab-183c54cf7862', N'101 Pine St', N'undraw_profile.svg', CAST(N'2023-04-20T09:45:00.0000000' AS DateTime2), N'FEMALE', N'STAFF', N'Emily Brown', N'4567890123', N'ACTIVE', CAST(N'2023-04-20T10:30:00.0000000' AS DateTime2), N'5be19781-d9ad-46d0-a916-a5588c84f15b')
INSERT [dbo].[system_users] ([id], [address], [avatar], [created_at], [gender], [level], [name], [phone], [system_user_status], [updated_at], [account_id]) VALUES (N'143c3bd9-fb5f-400a-ba56-a8873c5417ad', N'505 Elm St', N'undraw_profile.svg', CAST(N'2023-08-18T08:30:00.0000000' AS DateTime2), N'MALE', N'STAFF', N'James Davis', N'8901234567', N'ACTIVE', CAST(N'2023-08-18T09:10:00.0000000' AS DateTime2), N'473c80bc-2244-4a0e-b1b7-c6b260519bc8')
INSERT [dbo].[system_users] ([id], [address], [avatar], [created_at], [gender], [level], [name], [phone], [system_user_status], [updated_at], [account_id]) VALUES (N'1876767b-8fdc-4c28-a681-f49859132ac5', N'Go Vap', N'undraw_profile.svg', CAST(N'2024-11-20T20:40:04.4582170' AS DateTime2), N'MALE', N'STAFF', N'daniel', N'0158837298', N'ACTIVE', CAST(N'2024-11-20T20:40:04.4582170' AS DateTime2), N'4313c04b-2c40-4239-b0e8-a149606a9467')
INSERT [dbo].[system_users] ([id], [address], [avatar], [created_at], [gender], [level], [name], [phone], [system_user_status], [updated_at], [account_id]) VALUES (N'1a0b6b48-072f-4d13-be95-ce2051eab0de', N'909 Redwood St', N'undraw_profile.svg', CAST(N'2023-12-15T17:50:00.0000000' AS DateTime2), N'FEMALE', N'STAFF', N'Charlotte Gonzalez', N'2345678901', N'ACTIVE', CAST(N'2023-12-15T18:30:00.0000000' AS DateTime2), N'07d44ddd-76ac-4e83-8c6b-a33e6a865f93')
INSERT [dbo].[system_users] ([id], [address], [avatar], [created_at], [gender], [level], [name], [phone], [system_user_status], [updated_at], [account_id]) VALUES (N'1d407220-5074-4b72-8547-6ed83a18cd17', N'Go Vap', N'undraw_profile.svg', CAST(N'2024-11-20T20:36:25.4857990' AS DateTime2), N'MALE', N'MANAGER', N'Thanh Tam', N'0158837298', N'ACTIVE', CAST(N'2024-11-20T20:36:25.4868000' AS DateTime2), N'69fd74ee-118e-44a7-bfe2-6cb52d35f25e')
INSERT [dbo].[system_users] ([id], [address], [avatar], [created_at], [gender], [level], [name], [phone], [system_user_status], [updated_at], [account_id]) VALUES (N'26ebd74b-4efb-4419-9119-5bd7bc0d4d9e', N'202 Cedar St', N'undraw_profile.svg', CAST(N'2023-05-25T16:35:00.0000000' AS DateTime2), N'MALE', N'MANAGER', N'David Wilson', N'5678901234', N'ACTIVE', CAST(N'2023-05-25T17:15:00.0000000' AS DateTime2), N'cb2e5d3e-3640-4c6b-b058-10c65322b2f0')
INSERT [dbo].[system_users] ([id], [address], [avatar], [created_at], [gender], [level], [name], [phone], [system_user_status], [updated_at], [account_id]) VALUES (N'2c192a15-0a12-42de-94a0-0e068fe84223', N'1616 Elm St', N'undraw_profile.svg', CAST(N'2024-07-25T10:40:00.0000000' AS DateTime2), N'FEMALE', N'STAFF', N'Evelyn Lewis', N'9012345678', N'ACTIVE', CAST(N'2024-07-25T11:20:00.0000000' AS DateTime2), N'75094cd0-fb24-49e5-b35e-9b9fdde9b685')
INSERT [dbo].[system_users] ([id], [address], [avatar], [created_at], [gender], [level], [name], [phone], [system_user_status], [updated_at], [account_id]) VALUES (N'355bb490-6d4f-4f1a-b5f1-8e197fba7dd7', N'404 Walnut St', N'undraw_profile.svg', CAST(N'2023-07-15T12:50:00.0000000' AS DateTime2), N'FEMALE', N'DEPUTY_MANAGER', N'Sophia Miller', N'7890123456', N'ACTIVE', CAST(N'2023-07-15T13:40:00.0000000' AS DateTime2), N'2ce850c8-c235-4372-86f7-d7b43fa113e0')
INSERT [dbo].[system_users] ([id], [address], [avatar], [created_at], [gender], [level], [name], [phone], [system_user_status], [updated_at], [account_id]) VALUES (N'4320f3c5-3a03-400f-9be7-9ecf2ef2721c', N'Go Vap', N'undraw_profile.svg', CAST(N'2024-11-20T20:40:32.0484710' AS DateTime2), N'MALE', N'STAFF', N'grace', N'0158837298', N'ACTIVE', CAST(N'2024-11-20T20:40:32.0484710' AS DateTime2), N'899ef0e7-b1db-4400-9071-1aad06ad58c0')
INSERT [dbo].[system_users] ([id], [address], [avatar], [created_at], [gender], [level], [name], [phone], [system_user_status], [updated_at], [account_id]) VALUES (N'54f09084-9512-4084-b5d7-a455b56b0bb3', N'123 Main St', N'undraw_profile.svg', CAST(N'2023-01-15T08:30:00.0000000' AS DateTime2), N'MALE', N'MANAGER', N'John Doe', N'1234567890', N'ACTIVE', CAST(N'2023-01-15T09:00:00.0000000' AS DateTime2), N'c9e489e5-4882-4e8f-bab5-c3cef47e104d')
INSERT [dbo].[system_users] ([id], [address], [avatar], [created_at], [gender], [level], [name], [phone], [system_user_status], [updated_at], [account_id]) VALUES (N'61075c39-62a4-4f77-af36-0a3f4cc638b1', N'808 Fir St', N'1732975526539-undraw_profile.svg', CAST(N'2023-11-20T15:30:00.0000000' AS DateTime2), N'MALE', N'DEPUTY_MANAGER', N'Alex Lee 1', N'1234567890', N'ACTIVE', CAST(N'2024-11-30T21:05:26.5611060' AS DateTime2), N'f89f25e8-1cbc-4dcb-81b9-1c7e4e305a0c')
INSERT [dbo].[system_users] ([id], [address], [avatar], [created_at], [gender], [level], [name], [phone], [system_user_status], [updated_at], [account_id]) VALUES (N'6a3225c4-5072-43c2-80bf-2c946b0fae6f', N'707 Cypress St', N'undraw_profile.svg', CAST(N'2023-10-10T10:20:00.0000000' AS DateTime2), N'MALE', N'STAFF', N'Oliver Martinez', N'0123456789', N'ACTIVE', CAST(N'2023-10-10T11:00:00.0000000' AS DateTime2), N'd8a85423-b22b-4feb-b3f8-2abb77539199')
INSERT [dbo].[system_users] ([id], [address], [avatar], [created_at], [gender], [level], [name], [phone], [system_user_status], [updated_at], [account_id]) VALUES (N'6c694ded-a6fb-491e-942d-afac8bdf62a3', N'1010 Cedar St', N'undraw_profile.svg', CAST(N'2024-01-05T09:35:00.0000000' AS DateTime2), N'MALE', N'MANAGER', N'Benjamin Perez', N'3456789012', N'ACTIVE', CAST(N'2024-01-05T10:15:00.0000000' AS DateTime2), N'5d40c71a-f775-41fc-8ef9-89f2613e1ee3')
INSERT [dbo].[system_users] ([id], [address], [avatar], [created_at], [gender], [level], [name], [phone], [system_user_status], [updated_at], [account_id]) VALUES (N'6f300590-2e1f-4a2c-8686-944f7b9ff019', N'Go Vap', N'undraw_profile.svg', CAST(N'2024-11-20T20:39:37.5461860' AS DateTime2), N'MALE', N'STAFF', N'chrisharris', N'0158837298', N'ACTIVE', CAST(N'2024-11-20T20:39:37.5461860' AS DateTime2), N'06453093-9308-42ea-a9f2-0e91cc7a6109')
INSERT [dbo].[system_users] ([id], [address], [avatar], [created_at], [gender], [level], [name], [phone], [system_user_status], [updated_at], [account_id]) VALUES (N'74ccc2fe-2c13-45e3-8c5a-cda31db1bbdb', N'789 Oak St', N'undraw_profile.svg', CAST(N'2023-03-15T11:25:00.0000000' AS DateTime2), N'MALE', N'DEPUTY_MANAGER', N'Mike Johnson', N'3456789012', N'ACTIVE', CAST(N'2023-03-15T12:30:00.0000000' AS DateTime2), N'ebaba9bb-1fba-4d94-b967-ac19ec1ff975')
INSERT [dbo].[system_users] ([id], [address], [avatar], [created_at], [gender], [level], [name], [phone], [system_user_status], [updated_at], [account_id]) VALUES (N'773f4e08-f07a-4257-8432-8ae91d11d52b', N'1111 Maple St', N'undraw_profile.svg', CAST(N'2024-02-20T14:45:00.0000000' AS DateTime2), N'FEMALE', N'STAFF', N'Mia Hernandez', N'4567890123', N'ACTIVE', CAST(N'2024-02-20T15:25:00.0000000' AS DateTime2), N'98b9b703-dbb2-43f8-96d0-3daa3e91a45f')
INSERT [dbo].[system_users] ([id], [address], [avatar], [created_at], [gender], [level], [name], [phone], [system_user_status], [updated_at], [account_id]) VALUES (N'7a03c7ae-7bf2-4ade-ba67-f49dc539f4f1', N'303 Birch St', N'undraw_profile.svg', CAST(N'2023-06-10T18:20:00.0000000' AS DateTime2), N'MALE', N'STAFF', N'Chris Taylor', N'6789012345', N'ACTIVE', CAST(N'2023-06-10T19:00:00.0000000' AS DateTime2), N'0ebe2399-3a12-4115-a57e-bc2468e3160d')
INSERT [dbo].[system_users] ([id], [address], [avatar], [created_at], [gender], [level], [name], [phone], [system_user_status], [updated_at], [account_id]) VALUES (N'7d1db7b3-508d-4443-8b2e-336e0d0a60b6', N'1313 Pine St', N'undraw_profile.svg', CAST(N'2024-04-05T08:50:00.0000000' AS DateTime2), N'FEMALE', N'STAFF', N'Harper White', N'6789012345', N'ACTIVE', CAST(N'2024-04-05T09:30:00.0000000' AS DateTime2), N'0014f66b-5e66-48ca-be94-6c20276eccfe')
INSERT [dbo].[system_users] ([id], [address], [avatar], [created_at], [gender], [level], [name], [phone], [system_user_status], [updated_at], [account_id]) VALUES (N'84d645b4-6685-41b6-8a2e-239e83f46d09', N'qwe', N'1732718999756-dien-thoai-sony-xperia-10-vi.png', CAST(N'2024-11-27T21:49:59.9821570' AS DateTime2), N'FEMALE', N'MANAGER', N'SamSungGLX1', N'0123456789', N'ACTIVE', CAST(N'2024-11-27T21:49:59.9821570' AS DateTime2), N'b70c29da-7ea1-4d47-9d19-d83e2028c5ca')
INSERT [dbo].[system_users] ([id], [address], [avatar], [created_at], [gender], [level], [name], [phone], [system_user_status], [updated_at], [account_id]) VALUES (N'a5f401a5-4e9f-4fce-a96b-87a2fbe3ed2d', N'1414 Birch St', N'1732975538391-1732717420013-3.png', CAST(N'2024-05-10T11:20:00.0000000' AS DateTime2), N'FEMALE', N'DEPUTY_MANAGER', N'Amelia Harris', N'7890123456', N'ACTIVE', CAST(N'2024-11-30T21:05:38.4185500' AS DateTime2), N'929e39a1-17a4-4cbd-a361-76aaf2325def')
INSERT [dbo].[system_users] ([id], [address], [avatar], [created_at], [gender], [level], [name], [phone], [system_user_status], [updated_at], [account_id]) VALUES (N'b9fb6795-edb9-4ed1-9804-615b9e381f2a', N'Go Xoai', N'undraw_profile.svg', CAST(N'2024-08-18T15:00:00.0000000' AS DateTime2), N'MALE', N'MANAGER', N'Van Thanh', N'0934004524', N'ACTIVE', CAST(N'2024-08-18T15:45:00.0000000' AS DateTime2), N'95070376-aa41-474a-ac1f-d10d7463d030')
INSERT [dbo].[system_users] ([id], [address], [avatar], [created_at], [gender], [level], [name], [phone], [system_user_status], [updated_at], [account_id]) VALUES (N'ce60452d-d5c8-4fbd-8173-967a2857938b', N'Tan Phu', N'undraw_profile.svg', CAST(N'2024-11-20T20:35:50.5211590' AS DateTime2), N'MALE', N'MANAGER', N'Minh Duc', N'0158837298', N'ACTIVE', CAST(N'2024-11-20T20:35:50.5211590' AS DateTime2), N'35c5e35d-3f2f-48c3-90d1-7b35c24cbdf0')
INSERT [dbo].[system_users] ([id], [address], [avatar], [created_at], [gender], [level], [name], [phone], [system_user_status], [updated_at], [account_id]) VALUES (N'd714a7d1-9e79-4902-87a5-b7a9ce586780', N'456 Maple St', N'undraw_profile.svg', CAST(N'2023-02-10T14:10:00.0000000' AS DateTime2), N'FEMALE', N'STAFF', N'Jane Smith', N'2345678901', N'ACTIVE', CAST(N'2023-02-10T15:00:00.0000000' AS DateTime2), N'0a19b1f4-0ea5-41e5-9115-bec28fac6025')
INSERT [dbo].[system_users] ([id], [address], [avatar], [created_at], [gender], [level], [name], [phone], [system_user_status], [updated_at], [account_id]) VALUES (N'd9038a81-ed62-484a-85ad-a4919132db6f', N'1515 Walnut St', N'undraw_profile.svg', CAST(N'2024-06-15T13:30:00.0000000' AS DateTime2), N'MALE', N'MANAGER', N'Henry Clark', N'8901234567', N'ACTIVE', CAST(N'2024-06-15T14:10:00.0000000' AS DateTime2), N'7beb1cad-3134-4a30-9c27-377941b3f788')
INSERT [dbo].[system_users] ([id], [address], [avatar], [created_at], [gender], [level], [name], [phone], [system_user_status], [updated_at], [account_id]) VALUES (N'd972192c-1ae6-4abe-af77-eef1e8d1cf88', N'Binh Tan', N'1732716785677-dien-thoai-sony-xperia-10-vi.png', CAST(N'2024-11-20T20:37:42.6226230' AS DateTime2), N'MALE', N'MANAGER', N'Khanh Quang', N'0158837298', N'ACTIVE', CAST(N'2024-11-27T21:13:05.7391100' AS DateTime2), N'42be33a1-5193-4601-b14b-d04f667bad42')
INSERT [dbo].[system_users] ([id], [address], [avatar], [created_at], [gender], [level], [name], [phone], [system_user_status], [updated_at], [account_id]) VALUES (N'db85e46f-f43a-45ee-a56f-dd5e7ebb7630', N'606 Spruce St', N'undraw_profile.svg', CAST(N'2023-09-25T14:05:00.0000000' AS DateTime2), N'FEMALE', N'MANAGER', N'Isabella Garcia', N'9012345678', N'ACTIVE', CAST(N'2023-09-25T14:45:00.0000000' AS DateTime2), N'10aa62c0-374d-49b4-907f-891aab9b0ed5')
INSERT [dbo].[system_users] ([id], [address], [avatar], [created_at], [gender], [level], [name], [phone], [system_user_status], [updated_at], [account_id]) VALUES (N'e2cf5d72-da85-4775-9b6e-9923975c7813', N'Go Vap', N'undraw_profile.svg', CAST(N'2024-11-20T20:39:21.1781090' AS DateTime2), N'MALE', N'STAFF', N'charlotte', N'0158837298', N'ACTIVE', CAST(N'2024-11-20T20:39:21.1781090' AS DateTime2), N'4b9e2b88-69ba-4625-9cfb-a3ed864c2f61')
INSERT [dbo].[system_users] ([id], [address], [avatar], [created_at], [gender], [level], [name], [phone], [system_user_status], [updated_at], [account_id]) VALUES (N'ee32c2ac-d080-47e0-ba7e-247f21b879d7', N'1717 Spruce St', N'undraw_profile.svg', CAST(N'2024-08-18T15:00:00.0000000' AS DateTime2), N'MALE', N'DEPUTY_MANAGER', N'Jack Robinson', N'0123456789', N'ACTIVE', CAST(N'2024-08-18T15:45:00.0000000' AS DateTime2), N'f29408f0-e777-49bc-98fa-c431fe3fd941')
INSERT [dbo].[system_users] ([id], [address], [avatar], [created_at], [gender], [level], [name], [phone], [system_user_status], [updated_at], [account_id]) VALUES (N'f6696ec5-e6f5-48e8-893c-ffd2d16a1bae', N'1212 Oak St', N'undraw_profile.svg', CAST(N'2024-03-15T12:10:00.0000000' AS DateTime2), N'MALE', N'DEPUTY_MANAGER', N'Lucas Thompson', N'5678901234', N'ACTIVE', CAST(N'2024-03-15T12:50:00.0000000' AS DateTime2), N'88cc569a-b129-4d10-854d-206d22ba25c1')
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UKn7ihswpy07ci568w34q0oi8he]    Script Date: 18/05/2025 8:12:21 CH ******/
ALTER TABLE [dbo].[accounts] ADD  CONSTRAINT [UKn7ihswpy07ci568w34q0oi8he] UNIQUE NONCLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UKibvoggbe8ijw4l7xyyotp5n7g]    Script Date: 18/05/2025 8:12:21 CH ******/
ALTER TABLE [dbo].[blacklisted_tokens] ADD  CONSTRAINT [UKibvoggbe8ijw4l7xyyotp5n7g] UNIQUE NONCLUSTERED 
(
	[token] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UK4njtl3pvfduamug24b9qmpy0x]    Script Date: 18/05/2025 8:12:21 CH ******/
ALTER TABLE [dbo].[customers] ADD  CONSTRAINT [UK4njtl3pvfduamug24b9qmpy0x] UNIQUE NONCLUSTERED 
(
	[account_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UKm3iom37efaxd5eucmxjqqcbe9]    Script Date: 18/05/2025 8:12:21 CH ******/
CREATE UNIQUE NONCLUSTERED INDEX [UKm3iom37efaxd5eucmxjqqcbe9] ON [dbo].[customers]
(
	[phone] ASC
)
WHERE ([phone] IS NOT NULL)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UKnxp4me6ujkag9c0epydobulvd]    Script Date: 18/05/2025 8:12:21 CH ******/
ALTER TABLE [dbo].[system_users] ADD  CONSTRAINT [UKnxp4me6ujkag9c0epydobulvd] UNIQUE NONCLUSTERED 
(
	[account_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[account_role]  WITH CHECK ADD  CONSTRAINT [FKlxcbsh4odc9s9spjfw4jgxyqw] FOREIGN KEY([role_id])
REFERENCES [dbo].[roles] ([id])
GO
ALTER TABLE [dbo].[account_role] CHECK CONSTRAINT [FKlxcbsh4odc9s9spjfw4jgxyqw]
GO
ALTER TABLE [dbo].[account_role]  WITH CHECK ADD  CONSTRAINT [FKne5hjaf6i1wun5wnxlxvtl616] FOREIGN KEY([account_id])
REFERENCES [dbo].[accounts] ([id])
GO
ALTER TABLE [dbo].[account_role] CHECK CONSTRAINT [FKne5hjaf6i1wun5wnxlxvtl616]
GO
ALTER TABLE [dbo].[customers]  WITH CHECK ADD  CONSTRAINT [FKor0fx9fttvasr4grtaqnltyrl] FOREIGN KEY([account_id])
REFERENCES [dbo].[accounts] ([id])
GO
ALTER TABLE [dbo].[customers] CHECK CONSTRAINT [FKor0fx9fttvasr4grtaqnltyrl]
GO
ALTER TABLE [dbo].[permission_role]  WITH CHECK ADD  CONSTRAINT [FK3vhflqw0lwbwn49xqoivrtugt] FOREIGN KEY([role_id])
REFERENCES [dbo].[roles] ([id])
GO
ALTER TABLE [dbo].[permission_role] CHECK CONSTRAINT [FK3vhflqw0lwbwn49xqoivrtugt]
GO
ALTER TABLE [dbo].[permission_role]  WITH CHECK ADD  CONSTRAINT [FK6mg4g9rc8u87l0yavf8kjut05] FOREIGN KEY([permission_id])
REFERENCES [dbo].[permissions] ([id])
GO
ALTER TABLE [dbo].[permission_role] CHECK CONSTRAINT [FK6mg4g9rc8u87l0yavf8kjut05]
GO
ALTER TABLE [dbo].[system_users]  WITH CHECK ADD  CONSTRAINT [FKl0fnsqsjpr2ln9eqvsn50yi5v] FOREIGN KEY([account_id])
REFERENCES [dbo].[accounts] ([id])
GO
ALTER TABLE [dbo].[system_users] CHECK CONSTRAINT [FKl0fnsqsjpr2ln9eqvsn50yi5v]
GO
ALTER TABLE [dbo].[user_actions]  WITH CHECK ADD  CONSTRAINT [FKrffgqk162mnba39xgfwmnxhgo] FOREIGN KEY([customer_id])
REFERENCES [dbo].[customers] ([id])
GO
ALTER TABLE [dbo].[user_actions] CHECK CONSTRAINT [FKrffgqk162mnba39xgfwmnxhgo]
GO
ALTER TABLE [dbo].[customers]  WITH CHECK ADD CHECK  (([gender]='OTHER' OR [gender]='FEMALE' OR [gender]='MALE'))
GO
ALTER TABLE [dbo].[customers]  WITH CHECK ADD CHECK  (([gender]='OTHER' OR [gender]='FEMALE' OR [gender]='MALE'))
GO
ALTER TABLE [dbo].[customers]  WITH CHECK ADD CHECK  (([user_status]='SUSPENDED' OR [user_status]='INACTIVE' OR [user_status]='ACTIVE'))
GO
ALTER TABLE [dbo].[customers]  WITH CHECK ADD CHECK  (([user_status]='SUSPENDED' OR [user_status]='INACTIVE' OR [user_status]='ACTIVE'))
GO
ALTER TABLE [dbo].[system_users]  WITH CHECK ADD CHECK  (([gender]='OTHER' OR [gender]='FEMALE' OR [gender]='MALE'))
GO
ALTER TABLE [dbo].[system_users]  WITH CHECK ADD CHECK  (([gender]='OTHER' OR [gender]='FEMALE' OR [gender]='MALE'))
GO
ALTER TABLE [dbo].[system_users]  WITH CHECK ADD CHECK  (([level]='STAFF' OR [level]='DEPUTY_MANAGER' OR [level]='MANAGER'))
GO
ALTER TABLE [dbo].[system_users]  WITH CHECK ADD CHECK  (([level]='STAFF' OR [level]='DEPUTY_MANAGER' OR [level]='MANAGER'))
GO
ALTER TABLE [dbo].[system_users]  WITH CHECK ADD CHECK  (([system_user_status]='SUSPENDED' OR [system_user_status]='INACTIVE' OR [system_user_status]='ACTIVE'))
GO
ALTER TABLE [dbo].[system_users]  WITH CHECK ADD CHECK  (([system_user_status]='SUSPENDED' OR [system_user_status]='INACTIVE' OR [system_user_status]='ACTIVE'))
GO
ALTER TABLE [dbo].[user_actions]  WITH CHECK ADD CHECK  (([action_type]='SEARCH' OR [action_type]='VIEW' OR [action_type]='ADD_TO_CART' OR [action_type]='PURCHASE'))
GO
ALTER TABLE [dbo].[user_actions]  WITH CHECK ADD CHECK  (([action_type]='SEARCH' OR [action_type]='VIEW' OR [action_type]='ADD_TO_CART' OR [action_type]='PURCHASE'))
GO
USE [master]
GO
ALTER DATABASE [UserDB] SET  READ_WRITE 
GO
