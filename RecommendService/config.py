class Config:
    DB_DRIVER = '{ODBC Driver 18 for SQL Server}'
    DB_SERVER = 'host.docker.internal\\SQLEXPRESS'
    DB_NAME = 'TechGalaxy' 
    DB_USER = 'sa'
    DB_PASSWORD = 'sapassword'
    
    @staticmethod
    def get_connection_string():
        return f'DRIVER={Config.DB_DRIVER};SERVER={Config.DB_SERVER},1433;DATABASE={Config.DB_NAME};UID={Config.DB_USER};PWD={Config.DB_PASSWORD};Encrypt=no;TrustServerCertificate=yes'
    
    PRODUCT_DB_NAME = 'ProductDB'
    
    @staticmethod
    def get_product_connection_string():
        return f'DRIVER={Config.DB_DRIVER};SERVER={Config.DB_SERVER},1433;DATABASE={Config.PRODUCT_DB_NAME};UID={Config.DB_USER};PWD={Config.DB_PASSWORD};Encrypt=no;TrustServerCertificate=yes'
