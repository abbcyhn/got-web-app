import os
import psycopg2

def connect_to_db():
    try:
        conn = psycopg2.connect(
            dbname="postgres",
            user="postgres",
            password="mysecretpassword",
            host="localhost",
            port="5432"
        )
        conn.autocommit = True
        return conn
    except psycopg2.Error as e:
        print("error connecting to PostgreSQL database:", e)
        return None

def reset_schema(conn):
    try:
        cur = conn.cursor()
        cur.execute("DROP SCHEMA public CASCADE; CREATE SCHEMA public;")
        print("schema resetted successfully.")
    except psycopg2.Error as e:
        print("rrror resetting schema:", e)

def execute_sql_scripts(conn, folder_path):
    try:
        cur = conn.cursor()
        files = sorted(os.listdir(folder_path))
        for file in files:
            with open(os.path.join(folder_path, file), 'r') as sql_file:
                sql_script = sql_file.read()
                cur.execute(sql_script)
                print(f"executed script: {file}")
    except psycopg2.Error as e:
        print("error executing scripts", e)

conn = connect_to_db()
if conn is not None:
    folder_path = "sqls/"
    reset_schema(conn)
    execute_sql_scripts(conn, folder_path)
    conn.close()
