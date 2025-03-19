class Config:
    DB_DRIVER = '{ODBC Driver 17 for SQL Server}' 
    DB_SERVER = 'GGDUCK\\SQLEXPRESS' 
    DB_NAME = 'TechGalaxy'
    DB_USER = 'sa'
    DB_PASSWORD = 'sapassword'
    
    @staticmethod
    def get_connection_string():
        return f'DRIVER={Config.DB_DRIVER};SERVER={Config.DB_SERVER};DATABASE={Config.DB_NAME};UID={Config.DB_USER};PWD={Config.DB_PASSWORD};Encrypt=no;TrustServerCertificate=yes'

