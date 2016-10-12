package com.r.service

import groovy.sql.Sql

class DatabaseService {

    def grailsApplication
    def sessionFactory
    
    def createDatabase(){
        def sql1 = "CREATE DATABASE IF NOT EXISTS RSOLUTIONDEV"
        def sql2 = "CREATE DATABASE IF NOT EXISTS RSOLUTIONTEST"
        def sql3 = "CREATE DATABASE IF NOT EXISTS RSOLUTION"
        
        try{
            //def query = sessionfactory.execute(sql.toString())
            def datasource = grailsApplication.mainContext.getBean('dataSource')
            def conn = Sql.newInstance(datasource)
            try{
                conn.execute(sql1)
            }catch(Exception e){
                prinln e.toString()
            }
            
            try{
                conn.execute(sql2)
            }catch(Exception e){
                prinln e.toString()
            }
            
            try{
                conn.execute(sql3)
            }catch(Exception e){
                prinln e.toString()
            }
            
            conn.close()
        }catch(Exception e){
            println e.toString()
        }
    }
    
    def itemTriggers() {
        def sessionfactory = sessionFactory.currentSession
        
        def sql1drop = "DROP PROCEDURE IF EXISTS item_column_stock"
        def sql1 = """
                    CREATE PROCEDURE item_column_stock( IN columnname varchar(50) )
                    BEGIN
                        SET @Query = CONCAT( "ALTER TABLE item ADD COLUMN(", columnname , " DECIMAL(11,2) DEFAULT 0)" );
                        PREPARE STMT FROM @Query;
                        EXECUTE STMT;
                    END
                   """
        
        try{
            //def query = sessionfactory.execute(sql.toString())
            def datasource = grailsApplication.mainContext.getBean('dataSource')
            def conn = Sql.newInstance(datasource)
            try{
                conn.execute(sql1drop)
                conn.execute(sql1)
            }catch(Exception e){
                //prinln e.toString()
            }
            
            conn.close()
        }catch(Exception e){
            println e.toString()
        }
    }
    
    def locationTriggers(){
        def sessionfactory = sessionFactory.currentSession
        
        def sql1drop = "DROP PROCEDURE IF EXISTS item_column_stock"
        def sql1 = """
                    CREATE PROCEDURE item_column_stock( IN columnname varchar(50) )
                    BEGIN
                      SET @ddl = CONCAT( "ALTER TABLE item ADD COLUMN(", columnname , " INT(11) DEFAULT 0)" );
                      PREPARE STMT FROM @ddl;
                      EXECUTE STMT;
                    END
                   """      

        try{
            //def query = sessionfactory.execute(sql.toString())
            def datasource = grailsApplication.mainContext.getBean('dataSource')
            def conn = Sql.newInstance(datasource)
            
            try{
                conn.execute(sql1drop)
                conn.execute(sql1)
            }catch(Exception e){println e.toString()}
            
            conn.close()
        }catch(Exception e){
            println e.toString()
        }
    }
    
    def physicalTriggers(){
        def sessionfactory = sessionFactory.currentSession
        
        def sql1drop = "DROP TRIGGER IF EXISTS physical_stock_add"
        def sql1 = """
                    CREATE TRIGGER `physical_stock_add` AFTER INSERT
                    ON `physical_item`
                    FOR EACH ROW BEGIN
                        DECLARE stocknum NUMERIC;
                        SET stocknum = NEW.location_id; -- (SELECT MAX(a.location_id) FROM physical a WHERE a.physical_id = NEW.physical_id);

                        UPDATE item a SET a.stock = a.stock + ( NEW.qty * (SELECT packing FROM item_pack b WHERE b.item_id = NEW.item_id AND b.unit = NEW.unit) )
                        WHERE a.item_id = NEW.item_id;

                        CALL physical_stock_add_proc( stocknum, NEW.item_id, NEW.qty, NEW.unit );
                    END
                   """
        
        def sql2drop = "DROP TRIGGER IF EXISTS physical_stock_edit"
        def sql2 = """
                    CREATE TRIGGER `physical_stock_edit` AFTER UPDATE
                    ON `physical_item`
                    FOR EACH ROW BEGIN
                        DECLARE ostocknum NUMERIC;
                        DECLARE nstocknum NUMERIC;
                        SET ostocknum = OLD.location_id; -- (SELECT MAX(a.location_id) FROM physical a WHERE a.physical_id = OLD.physical_id);
                        SET nstocknum = NEW.location_id; -- (SELECT MAX(a.location_id) FROM physical a WHERE a.physical_id = NEW.physical_id);

                        IF NEW.item_id = OLD.item_id THEN
                            UPDATE item a SET a.stock = a.stock - ( OLD.qty * (SELECT packing FROM item_pack b WHERE b.item_id = OLD.item_id AND b.unit = OLD.unit) )
                                                                + ( NEW.qty * (SELECT packing FROM item_pack b WHERE b.item_id = NEW.item_id AND b.unit = NEW.unit) )
                            WHERE a.item_id = NEW.item_id;
                        ELSE
                            UPDATE item a SET a.stock = a.stock - ( OLD.qty * (SELECT packing FROM item_pack b WHERE b.item_id = OLD.item_id AND b.unit = OLD.unit) )
                            WHERE a.item_id = OLD.item_id;
                            
                            UPDATE item a SET a.stock = a.stock + ( NEW.qty * (SELECT packing FROM item_pack b WHERE b.item_id = NEW.item_id AND b.unit = NEW.unit) )
                            WHERE a.item_id = NEW.item_id;
                        END IF;

                        CALL physical_stock_edit_proc( ostocknum, nstocknum, NEW.item_id, NEW.qty, NEW.unit, OLD.item_id, OLD.qty, OLD.unit );
                    END
                   """
        
        def sql3drop = "DROP TRIGGER IF EXISTS physical_stock_delete"
        def sql3 = """
                    CREATE TRIGGER `physical_stock_delete` BEFORE DELETE
                    ON `physical_item`
                    FOR EACH ROW BEGIN
                        DECLARE stocknum NUMERIC;
                        SET stocknum = OLD.location_id; -- (SELECT MAX(a.location_id) FROM physical a WHERE a.physical_id = OLD.physical_id);

                        UPDATE item a SET a.stock = a.stock - ( OLD.qty * (SELECT packing FROM item_pack b WHERE b.item_id = OLD.item_id AND b.unit = OLD.unit) )
                        WHERE a.item_id = OLD.item_id;

                        CALL physical_stock_delete_proc( stocknum, OLD.item_id, OLD.qty, OLD.unit );
                    END
                   """
       
        try{
            //def query = sessionfactory.execute(sql.toString())
            def datasource = grailsApplication.mainContext.getBean('dataSource')
            def conn = Sql.newInstance(datasource)
            try{
                conn.execute(sql1drop)
                conn.execute(sql1)
            }catch(Exception e){
                //println e.toString()
            }
            try{
                conn.execute(sql2drop)
                conn.execute(sql2)
            }catch(Exception e){
                println e.toString()
            }
            try{
                conn.execute(sql3drop)
                conn.execute(sql3)
            }catch(Exception e){
                println e.toString()
            }
            conn.close()
        }catch(Exception e){
            println e.toString()
        }
    }
    
    def physicalProcedures(){
        def sessionfactory = sessionFactory.currentSession
        
        def sql1locadd  = """"""
        def sql1locedt  = """"""
        def sql1locdlt  = """"""
        def sqllocation = "SELECT location_id FROM location"
        
        def query = sessionfactory.createSQLQuery(sqllocation)
        query.list().each{row->
            sql1locadd  = sql1locadd + """
                                        IF stocknum = $row THEN
                                            UPDATE item a SET a.stock_$row = a.stock_$row + ( nqty * (SELECT packing FROM item_pack b WHERE b.item_id = nitem AND b.unit = nunit) )
                                            WHERE a.item_id = nitem;
                                        END IF;
                                       """
            
            sql1locedt  = sql1locedt + """ 
                                        IF nstocknum != ostocknum THEN

                                            IF ostocknum = $row THEN
                                                UPDATE item a SET a.stock_$row = a.stock_$row - ( oqty * (SELECT packing FROM item_pack b WHERE b.item_id = oitem AND b.unit = ounit) )
                                                WHERE a.item_id = oitem;
                                            END IF;

                                            IF nstocknum = $row THEN
                                                UPDATE item a SET a.stock_$row = a.stock_$row + ( nqty * (SELECT packing FROM item_pack b WHERE b.item_id = nitem AND b.unit = nunit) )
                                                WHERE a.item_id = nitem;
                                            END IF;

                                        ELSE 
                                            IF nstocknum = $row THEN
                                                IF nitem = oitem THEN
                                                    UPDATE item a SET a.stock_$row = a.stock_$row - ( oqty * (SELECT packing FROM item_pack b WHERE b.item_id = oitem AND b.unit = ounit) )
                                                                                                  + ( nqty * (SELECT packing FROM item_pack b WHERE b.item_id = nitem AND b.unit = nunit) )
                                                    WHERE a.item_id = nitem;
                                                ELSE
                                                    UPDATE item a SET a.stock_$row = a.stock_$row - ( oqty * (SELECT packing FROM item_pack b WHERE b.item_id = oitem AND b.unit = ounit) )
                                                    WHERE a.item_id = oitem;

                                                    UPDATE item a SET a.stock_$row = a.stock_$row + ( nqty * (SELECT packing FROM item_pack b WHERE b.item_id = nitem AND b.unit = nunit) )
                                                    WHERE a.item_id = nitem;
                                                END IF;
                                            END IF;
                                        END IF;
                                       """
            
            sql1locdlt  = sql1locdlt + """
                                        IF stocknum = $row THEN
                                            UPDATE item a SET a.stock_$row = a.stock_$row - ( oqty * (SELECT packing FROM item_pack b WHERE b.item_id = oitem AND b.unit = ounit) )
                                            WHERE a.item_id = oitem;
                                        END IF;
                                       """
        }
        
        def sql1drop = "DROP PROCEDURE IF EXISTS physical_stock_add_proc"
        def sql1 = """
                    CREATE PROCEDURE physical_stock_add_proc( IN stocknum varchar(50), IN nitem varchar(50), IN nqty DECIMAL(12,2), IN nunit varchar(50) )
                    BEGIN
                   """
            sql1 = sql1 + sql1locadd
            sql1 = sql1 + """ 
                    END
                   """
        
        def sql2drop = "DROP PROCEDURE IF EXISTS physical_stock_edit_proc"
        def sql2 = """
                    CREATE PROCEDURE physical_stock_edit_proc( IN ostocknum varchar(50), IN nstocknum varchar(50), IN nitem varchar(50), IN nqty DECIMAL(12,2), IN nunit varchar(50), IN oitem varchar(50), IN oqty DECIMAL(12,2), IN ounit varchar(50) )
                    BEGIN
                   """
            sql2 = sql2 + sql1locedt
            sql2 = sql2 + """ 
                    END
                   """
        
        def sql3drop = "DROP PROCEDURE IF EXISTS physical_stock_delete_proc"
        def sql3 = """
                    CREATE PROCEDURE physical_stock_delete_proc( IN stocknum varchar(50), IN oitem varchar(50), IN oqty DECIMAL(12,2), IN ounit varchar(50) )
                    BEGIN
                   """
            sql3 = sql3 + sql1locdlt
            sql3 = sql3 + """ 
                    END
                   """
                   
        
        try{
            //def query = sessionfactory.execute(sql.toString())
            def datasource = grailsApplication.mainContext.getBean('dataSource')
            def conn = Sql.newInstance(datasource)
            
            try{
                conn.execute(sql1drop)
                conn.execute(sql1)
            }catch(Exception e){
                println e.toString()
            }
            
            try{
                conn.execute(sql2drop)
                conn.execute(sql2)
            }catch(Exception e){
                println e.toString()
            }
            
            try{
                conn.execute(sql3drop)
                conn.execute(sql3)
            }catch(Exception e){
                println e.toString()
            }
            
            conn.close()
        }catch(Exception e){
            println e.toString()
        }
    }
    
    def withdrawalTriggers(){
        def sessionfactory = sessionFactory.currentSession
        
        def sql1drop = "DROP TRIGGER IF EXISTS withdrawal_stock_add"
        def sql1 = """
                    CREATE TRIGGER `withdrawal_stock_add` AFTER INSERT
                    ON `withdrawal_item`
                    FOR EACH ROW BEGIN
                        DECLARE stocknum NUMERIC;
                        SET stocknum =  NEW.location_id;

                        UPDATE item a SET a.stock = a.stock - ( NEW.qty * (SELECT packing FROM item_pack b WHERE b.item_id = NEW.item_id AND b.unit = NEW.unit) )
                        WHERE a.item_id = NEW.item_id;

                        CALL withdrawal_stock_add_proc( stocknum, NEW.item_id, NEW.qty, NEW.unit );
                    END
                   """
        
        def sql2drop = "DROP TRIGGER IF EXISTS withdrawal_stock_edit"
        def sql2 = """
                    CREATE TRIGGER `withdrawal_stock_edit` AFTER UPDATE
                    ON `withdrawal_item`
                    FOR EACH ROW BEGIN
                        DECLARE ostocknum NUMERIC;
                        DECLARE nstocknum NUMERIC;
                        SET ostocknum = OLD.location_id;
                        SET nstocknum = NEW.location_id;

                        IF NEW.item_id = OLD.item_id THEN
                            UPDATE item a SET a.stock = a.stock + ( OLD.qty * (SELECT packing FROM item_pack b WHERE b.item_id = OLD.item_id AND b.unit = OLD.unit) )
                                                                - ( NEW.qty * (SELECT packing FROM item_pack b WHERE b.item_id = NEW.item_id AND b.unit = NEW.unit) )
                            WHERE a.item_id = NEW.item_id;
                        ELSE
                            UPDATE item a SET a.stock = a.stock + ( OLD.qty * (SELECT packing FROM item_pack b WHERE b.item_id = OLD.item_id AND b.unit = OLD.unit) )
                            WHERE a.item_id = OLD.item_id;
                            
                            UPDATE item a SET a.stock = a.stock - ( NEW.qty * (SELECT packing FROM item_pack b WHERE b.item_id = NEW.item_id AND b.unit = NEW.unit) )
                            WHERE a.item_id = NEW.item_id;
                        END IF;

                        CALL withdrawal_stock_edit_proc( ostocknum, nstocknum, NEW.item_id, NEW.qty, NEW.unit, OLD.item_id, OLD.qty, OLD.unit );
                    END
                   """
        
        def sql3drop = "DROP TRIGGER IF EXISTS withdrawal_stock_delete"
        def sql3 = """
                    CREATE TRIGGER `withdrawal_stock_delete` BEFORE DELETE
                    ON `withdrawal_item`
                    FOR EACH ROW BEGIN
                        DECLARE stocknum NUMERIC;
                        SET stocknum = OLD.location_id;

                        UPDATE item a SET a.stock = a.stock + ( OLD.qty * (SELECT packing FROM item_pack b WHERE b.item_id = OLD.item_id AND b.unit = OLD.unit) )
                        WHERE a.item_id = OLD.item_id;

                        CALL withdrawal_stock_delete_proc( stocknum, OLD.item_id, OLD.qty, OLD.unit );
                    END
                   """
       
        try{
            //def query = sessionfactory.execute(sql.toString())
            def datasource = grailsApplication.mainContext.getBean('dataSource')
            def conn = Sql.newInstance(datasource)
            try{
                conn.execute(sql1drop)
                conn.execute(sql1)
            }catch(Exception e){
                //println e.toString()
            }
            try{
                conn.execute(sql2drop)
                conn.execute(sql2)
            }catch(Exception e){
                println e.toString()
            }
            try{
                conn.execute(sql3drop)
                conn.execute(sql3)
            }catch(Exception e){
                println e.toString()
            }
            conn.close()
        }catch(Exception e){
            println e.toString()
        }
    }
    
    def withdrawalProcedures(){
        def sessionfactory = sessionFactory.currentSession
        
        def sql1locadd  = """"""
        def sql1locedt  = """"""
        def sql1locdlt  = """"""
        def sqllocation = "SELECT location_id FROM location"
        
        def query = sessionfactory.createSQLQuery(sqllocation)
        query.list().each{row->
            sql1locadd  = sql1locadd + """
                                        IF stocknum = $row THEN
                                            UPDATE item a SET a.stock_$row = a.stock_$row - ( nqty * (SELECT packing FROM item_pack b WHERE b.item_id = nitem AND b.unit = nunit) )
                                            WHERE a.item_id = nitem;
                                        END IF;
                                       """
            
            sql1locedt  = sql1locedt + """
                                        IF nstocknum != ostocknum THEN

                                            IF ostocknum = $row THEN
                                                UPDATE item a SET a.stock_$row = a.stock_$row + ( oqty * (SELECT packing FROM item_pack b WHERE b.item_id = oitem AND b.unit = ounit) )
                                                WHERE a.item_id = oitem;
                                            END IF;

                                            IF nstocknum = $row THEN
                                                UPDATE item a SET a.stock_$row = a.stock_$row - ( nqty * (SELECT packing FROM item_pack b WHERE b.item_id = nitem AND b.unit = nunit) )
                                                WHERE a.item_id = nitem;
                                            END IF;

                                        ELSE 
                                            IF nstocknum = $row THEN
                                                IF nitem = oitem THEN
                                                    UPDATE item a SET a.stock_$row = a.stock_$row + ( oqty * (SELECT packing FROM item_pack b WHERE b.item_id = oitem AND b.unit = ounit) )
                                                                                                  - ( nqty * (SELECT packing FROM item_pack b WHERE b.item_id = nitem AND b.unit = nunit) )
                                                    WHERE a.item_id = nitem;
                                                ELSE
                                                    UPDATE item a SET a.stock_$row = a.stock_$row + ( oqty * (SELECT packing FROM item_pack b WHERE b.item_id = oitem AND b.unit = ounit) )
                                                    WHERE a.item_id = OLD.item_id;

                                                    UPDATE item a SET a.stock_$row = a.stock_$row - ( nqty * (SELECT packing FROM item_pack b WHERE b.item_id = nitem AND b.unit = nunit) )
                                                    WHERE a.item_id = nitem;
                                                END IF;
                                            END IF;
                                        END IF;
                                       """
            
            sql1locdlt  = sql1locdlt + """
                                        IF stocknum = $row THEN
                                            UPDATE item a SET a.stock_$row = a.stock_$row + ( oqty * (SELECT packing FROM item_pack b WHERE b.item_id = oitem AND b.unit = ounit) )
                                            WHERE a.item_id = oitem;
                                        END IF;
                                       """
        }
        
        def sql1drop = "DROP PROCEDURE IF EXISTS withdrawal_stock_add_proc"
        def sql1 = """
                    CREATE PROCEDURE withdrawal_stock_add_proc( IN stocknum varchar(50), IN nitem varchar(50), IN nqty DECIMAL(12,2), IN nunit varchar(50) )
                    BEGIN
                   """
            sql1 = sql1 + sql1locadd
            sql1 = sql1 + """ 
                    END
                   """
        
        def sql2drop = "DROP PROCEDURE IF EXISTS withdrawal_stock_edit_proc"
        def sql2 = """
                    CREATE PROCEDURE withdrawal_stock_edit_proc( IN ostocknum varchar(50), IN nstocknum varchar(50), IN nitem varchar(50), IN nqty DECIMAL(12,2), IN nunit varchar(50), IN oitem varchar(50), IN oqty DECIMAL(12,2), IN ounit varchar(50) )
                    BEGIN
                   """
            sql2 = sql2 + sql1locedt
            sql2 = sql2 + """ 
                    END
                   """
        
        def sql3drop = "DROP PROCEDURE IF EXISTS withdrawal_stock_delete_proc"
        def sql3 = """
                    CREATE PROCEDURE withdrawal_stock_delete_proc( IN stocknum varchar(50), IN oitem varchar(50), IN oqty DECIMAL(12,2), IN ounit varchar(50) )
                    BEGIN
                   """
            sql3 = sql3 + sql1locdlt
            sql3 = sql3 + """ 
                    END
                   """
                   
        
        try{
            //def query = sessionfactory.execute(sql.toString())
            def datasource = grailsApplication.mainContext.getBean('dataSource')
            def conn = Sql.newInstance(datasource)
            
            try{
                conn.execute(sql1drop)
                conn.execute(sql1)
            }catch(Exception e){
                println e.toString()
            }
            
            try{
                conn.execute(sql2drop)
                conn.execute(sql2)
            }catch(Exception e){
                println e.toString()
            }
            
            try{
                conn.execute(sql3drop)
                conn.execute(sql3)
            }catch(Exception e){
                println e.toString()
            }
            
            conn.close()
        }catch(Exception e){
            println e.toString()
        }
    }
    
    def receivingTriggers(){
        def sessionfactory = sessionFactory.currentSession
        
        def sql1drop = "DROP TRIGGER IF EXISTS receiving_stock_add"
        def sql1 = """
                    CREATE TRIGGER `receiving_stock_add` AFTER INSERT
                    ON `receiving_item`
                    FOR EACH ROW BEGIN
                        DECLARE stocknum NUMERIC;
                        SET stocknum = NEW.location_id;

                        UPDATE item a SET a.stock = a.stock + ( NEW.qty * (SELECT packing FROM item_pack b WHERE b.item_id = NEW.item_id AND b.unit = NEW.unit) )
                        WHERE a.item_id = NEW.item_id;

                        CALL receiving_stock_add_proc( stocknum, NEW.item_id, NEW.qty, NEW.unit );
                    END
                   """
        
        def sql2drop = "DROP TRIGGER IF EXISTS receiving_stock_edit"
        def sql2 = """
                    CREATE TRIGGER `receiving_stock_edit` AFTER UPDATE
                    ON `receiving_item`
                    FOR EACH ROW BEGIN
                        DECLARE ostocknum NUMERIC;
                        DECLARE nstocknum NUMERIC;
                        SET ostocknum = OLD.location_id;
                        SET nstocknum = NEW.location_id;

                        IF NEW.item_id = OLD.item_id THEN
                            UPDATE item a SET a.stock = a.stock - ( OLD.qty * (SELECT packing FROM item_pack b WHERE b.item_id = OLD.item_id AND b.unit = OLD.unit) )
                                                                + ( NEW.qty * (SELECT packing FROM item_pack b WHERE b.item_id = NEW.item_id AND b.unit = NEW.unit) )
                            WHERE a.item_id = NEW.item_id;
                        ELSE
                            UPDATE item a SET a.stock = a.stock - ( OLD.qty * (SELECT packing FROM item_pack b WHERE b.item_id = OLD.item_id AND b.unit = OLD.unit) )
                            WHERE a.item_id = OLD.item_id;
                            
                            UPDATE item a SET a.stock = a.stock + ( NEW.qty * (SELECT packing FROM item_pack b WHERE b.item_id = NEW.item_id AND b.unit = NEW.unit) )
                            WHERE a.item_id = NEW.item_id;
                        END IF;
                        
                        CALL receiving_stock_edit_proc( ostocknum, nstocknum, NEW.item_id, NEW.qty, NEW.unit, OLD.item_id, OLD.qty, OLD.unit );
                    END
                   """
        
        def sql3drop = "DROP TRIGGER IF EXISTS receiving_stock_delete"
        def sql3 = """
                    CREATE TRIGGER `receiving_stock_delete` BEFORE DELETE
                    ON `receiving_item`
                    FOR EACH ROW BEGIN
                        DECLARE stocknum NUMERIC;
                        SET stocknum = OLD.location_id;

                        UPDATE item a SET a.stock = a.stock - ( OLD.qty * (SELECT packing FROM item_pack b WHERE b.item_id = OLD.item_id AND b.unit = OLD.unit) )
                        WHERE a.item_id = OLD.item_id;

                        CALL receiving_stock_delete_proc( stocknum, OLD.item_id, OLD.qty, OLD.unit );
                    END
                   """
       
        try{
            //def query = sessionfactory.execute(sql.toString())
            def datasource = grailsApplication.mainContext.getBean('dataSource')
            def conn = Sql.newInstance(datasource)
            try{
                conn.execute(sql1drop)
                conn.execute(sql1)
            }catch(Exception e){
                //println e.toString()
            }
            try{
                conn.execute(sql2drop)
                conn.execute(sql2)
            }catch(Exception e){
                println e.toString()
            }
            try{
                conn.execute(sql3drop)
                conn.execute(sql3)
            }catch(Exception e){
                println e.toString()
            }
            conn.close()
        }catch(Exception e){
            println e.toString()
        }
    }
    
    def receivingProcedures(){
        def sessionfactory = sessionFactory.currentSession
        
        def sql1locadd  = """"""
        def sql1locedt  = """"""
        def sql1locdlt  = """"""
        def sqllocation = "SELECT location_id FROM location"
        
        def query = sessionfactory.createSQLQuery(sqllocation)
        query.list().each{row->
            sql1locadd  = sql1locadd + """
                                        IF stocknum = $row THEN
                                            UPDATE item a SET a.stock_$row = a.stock_$row + ( nqty * (SELECT packing FROM item_pack b WHERE b.item_id = nitem AND b.unit = nunit) )
                                            WHERE a.item_id = nitem;
                                        END IF;
                                       """
            
            sql1locedt  = sql1locedt + """
                                        IF nstocknum != ostocknum THEN

                                            IF ostocknum = $row THEN
                                                UPDATE item a SET a.stock_$row = a.stock_$row - ( oqty * (SELECT packing FROM item_pack b WHERE b.item_id = oitem AND b.unit = ounit) )
                                                WHERE a.item_id = oitem;
                                            END IF;

                                            IF nstocknum = $row THEN
                                                UPDATE item a SET a.stock_$row = a.stock_$row + ( nqty * (SELECT packing FROM item_pack b WHERE b.item_id = nitem AND b.unit = nunit) )
                                                WHERE a.item_id = nitem;
                                            END IF;

                                        ELSE 
                                            IF nstocknum = $row THEN
                                                IF nitem = oitem THEN
                                                    UPDATE item a SET a.stock_$row = a.stock_$row - ( oqty * (SELECT packing FROM item_pack b WHERE b.item_id = oitem AND b.unit = ounit) )
                                                                                                  + ( nqty * (SELECT packing FROM item_pack b WHERE b.item_id = nitem AND b.unit = nunit) )
                                                    WHERE a.item_id = nitem;
                                                ELSE
                                                    UPDATE item a SET a.stock_$row = a.stock_$row - ( oqty * (SELECT packing FROM item_pack b WHERE b.item_id = oitem AND b.unit = ounit) )
                                                    WHERE a.item_id = OLD.item_id;

                                                    UPDATE item a SET a.stock_$row = a.stock_$row + ( nqty * (SELECT packing FROM item_pack b WHERE b.item_id = nitem AND b.unit = nunit) )
                                                    WHERE a.item_id = nitem;
                                                END IF;
                                            END IF;
                                        END IF;
                                       """
            
            sql1locdlt  = sql1locdlt + """
                                        IF stocknum = $row THEN
                                            UPDATE item a SET a.stock_$row = a.stock_$row - ( oqty * (SELECT packing FROM item_pack b WHERE b.item_id = oitem AND b.unit = ounit) )
                                            WHERE a.item_id = oitem;
                                        END IF;
                                       """
        }
        
        def sql1drop = "DROP PROCEDURE IF EXISTS receiving_stock_add_proc"
        def sql1 = """
                    CREATE PROCEDURE receiving_stock_add_proc( IN stocknum varchar(50), IN nitem varchar(50), IN nqty DECIMAL(12,2), IN nunit varchar(50) )
                    BEGIN
                   """
            sql1 = sql1 + sql1locadd
            sql1 = sql1 + """ 
                    END
                   """
        
        def sql2drop = "DROP PROCEDURE IF EXISTS receiving_stock_edit_proc"
        def sql2 = """
                    CREATE PROCEDURE receiving_stock_edit_proc( IN ostocknum varchar(50), IN nstocknum varchar(50), IN nitem varchar(50), IN nqty DECIMAL(12,2), IN nunit varchar(50), IN oitem varchar(50), IN oqty DECIMAL(12,2), IN ounit varchar(50) )
                    BEGIN
                   """
            sql2 = sql2 + sql1locedt
            sql2 = sql2 + """ 
                    END
                   """
        
        def sql3drop = "DROP PROCEDURE IF EXISTS receiving_stock_delete_proc"
        def sql3 = """
                    CREATE PROCEDURE receiving_stock_delete_proc( IN stocknum varchar(50), IN oitem varchar(50), IN oqty DECIMAL(12,2), IN ounit varchar(50) )
                    BEGIN
                   """
            sql3 = sql3 + sql1locdlt
            sql3 = sql3 + """ 
                    END
                   """
                   
        
        try{
            //def query = sessionfactory.execute(sql.toString())
            def datasource = grailsApplication.mainContext.getBean('dataSource')
            def conn = Sql.newInstance(datasource)
            
            try{
                conn.execute(sql1drop)
                conn.execute(sql1)
            }catch(Exception e){
                println e.toString()
            }
            
            try{
                conn.execute(sql2drop)
                conn.execute(sql2)
            }catch(Exception e){
                println e.toString()
            }
            
            try{
                conn.execute(sql3drop)
                conn.execute(sql3)
            }catch(Exception e){
                println e.toString()
            }
            
            conn.close()
        }catch(Exception e){
            println e.toString()
        }
    }
    
    def adjustTriggers(){
        def sessionfactory = sessionFactory.currentSession
        
        def sql1drop = "DROP TRIGGER IF EXISTS adjust_stock_add"
        def sql1 = """
                    CREATE TRIGGER `adjust_stock_add` AFTER INSERT
                    ON `adjust_item`
                    FOR EACH ROW BEGIN
                        DECLARE stocknum NUMERIC;
                        DECLARE adjust VARCHAR(255);
                        SET adjust = (SELECT MAX(a.type) FROM adjust a WHERE a.adjust_id = NEW.adjust_id);
                        SET stocknum = NEW.location_id;
                        
                        IF adjust = 'IN' THEN
                            UPDATE item a SET a.stock = a.stock + ( NEW.qty * (SELECT packing FROM item_pack b WHERE b.item_id = NEW.item_id AND b.unit = NEW.unit) )
                            WHERE a.item_id = NEW.item_id;
                        ELSE
                            UPDATE item a SET a.stock = a.stock - ( NEW.qty * (SELECT packing FROM item_pack b WHERE b.item_id = NEW.item_id AND b.unit = NEW.unit) )
                            WHERE a.item_id = NEW.item_id;
                        END IF;

                        CALL adjust_stock_add_proc( stocknum, NEW.item_id, NEW.qty, NEW.unit, adjust );
                    END
                   """
        
        def sql2drop = "DROP TRIGGER IF EXISTS adjust_stock_edit"
        def sql2 = """
                    CREATE TRIGGER `adjust_stock_edit` AFTER UPDATE
                    ON `adjust_item`
                    FOR EACH ROW BEGIN
                        DECLARE ostocknum NUMERIC;
                        DECLARE nstocknum NUMERIC;
                        DECLARE adjust VARCHAR(255);
                        DECLARE oadjust VARCHAR(255);
                        DECLARE nadjust VARCHAR(255);
                        SET ostocknum = OLD.location_id;
                        SET nstocknum = NEW.location_id;
                        SET adjust = (SELECT MAX(a.type) FROM adjust a WHERE a.adjust_id = NEW.adjust_id);
                        SET oadjust = '';
                        SET nadjust = '';

                        IF adjust = 'IN' THEN
                            IF NEW.item_id = OLD.item_id THEN
                                UPDATE item a SET a.stock = a.stock - ( OLD.qty * (SELECT packing FROM item_pack b WHERE b.item_id = OLD.item_id AND b.unit = OLD.unit) )
                                                                    + ( NEW.qty * (SELECT packing FROM item_pack b WHERE b.item_id = NEW.item_id AND b.unit = NEW.unit) )
                                WHERE a.item_id = NEW.item_id;
                            ELSE
                                UPDATE item a SET a.stock = a.stock - ( OLD.qty * (SELECT packing FROM item_pack b WHERE b.item_id = OLD.item_id AND b.unit = OLD.unit) )
                                WHERE a.item_id = OLD.item_id;

                                UPDATE item a SET a.stock = a.stock + ( NEW.qty * (SELECT packing FROM item_pack b WHERE b.item_id = NEW.item_id AND b.unit = NEW.unit) )
                                WHERE a.item_id = NEW.item_id;
                            END IF;
                        ELSE
                            IF NEW.item_id = OLD.item_id THEN
                                UPDATE item a SET a.stock = a.stock + ( OLD.qty * (SELECT packing FROM item_pack b WHERE b.item_id = OLD.item_id AND b.unit = OLD.unit) )
                                                                    - ( NEW.qty * (SELECT packing FROM item_pack b WHERE b.item_id = NEW.item_id AND b.unit = NEW.unit) )
                                WHERE a.item_id = NEW.item_id;
                            ELSE
                                UPDATE item a SET a.stock = a.stock + ( OLD.qty * (SELECT packing FROM item_pack b WHERE b.item_id = OLD.item_id AND b.unit = OLD.unit) )
                                WHERE a.item_id = OLD.item_id;

                                UPDATE item a SET a.stock = a.stock - ( NEW.qty * (SELECT packing FROM item_pack b WHERE b.item_id = NEW.item_id AND b.unit = NEW.unit) )
                                WHERE a.item_id = NEW.item_id;
                            END IF;
                        END IF;

                        CALL adjust_stock_edit_proc( ostocknum, nstocknum, NEW.item_id, NEW.qty, NEW.unit, OLD.item_id, OLD.qty, OLD.unit, oadjust, nadjust, adjust);
                    END
                   """
        
        def sql3drop = "DROP TRIGGER IF EXISTS adjust_stock_delete"
        def sql3 = """
                    CREATE TRIGGER `adjust_stock_delete` BEFORE DELETE
                    ON `adjust_item`
                    FOR EACH ROW BEGIN
                        DECLARE stocknum NUMERIC;
                        DECLARE adjust VARCHAR(255);
                        SET adjust = (SELECT MAX(a.type) FROM adjust a WHERE a.adjust_id = OLD.adjust_id);
                        SET stocknum = OLD.location_id;

                        IF adjust = 'IN' THEN
                            UPDATE item a SET a.stock = a.stock - ( OLD.qty * (SELECT packing FROM item_pack b WHERE b.item_id = OLD.item_id AND b.unit = OLD.unit) )
                            WHERE a.item_id = OLD.item_id;
                        ELSE
                            UPDATE item a SET a.stock = a.stock + ( OLD.qty * (SELECT packing FROM item_pack b WHERE b.item_id = OLD.item_id AND b.unit = OLD.unit) )
                            WHERE a.item_id = OLD.item_id;
                        END IF;

                        CALL adjust_stock_delete_proc( stocknum, OLD.item_id, OLD.qty, OLD.unit, adjust );
                    END
                   """
       
        try{
            //def query = sessionfactory.execute(sql.toString())
            def datasource = grailsApplication.mainContext.getBean('dataSource')
            def conn = Sql.newInstance(datasource)
            try{
                conn.execute(sql1drop)
                conn.execute(sql1)
            }catch(Exception e){
                //println e.toString()
            }
            try{
                conn.execute(sql2drop)
                conn.execute(sql2)
            }catch(Exception e){
                println e.toString()
            }
            try{
                conn.execute(sql3drop)
                conn.execute(sql3)
            }catch(Exception e){
                println e.toString()
            }
            conn.close()
        }catch(Exception e){
            println e.toString()
        }
    }
    
    def adjustProcedures(){
        def sessionfactory = sessionFactory.currentSession
        
        def sql1locadd  = """"""
        def sql1locedt  = """"""
        def sql1locdlt  = """"""
        def sqllocation = "SELECT location_id FROM location"
        
        def query = sessionfactory.createSQLQuery(sqllocation)
        query.list().each{row->
            sql1locadd  = sql1locadd + """
                                        IF stocknum = $row THEN
                                            IF adjust = 'IN' THEN
                                                UPDATE item a SET a.stock_$row = a.stock_$row + ( nqty * (SELECT packing FROM item_pack b WHERE b.item_id = nitem AND b.unit = nunit) )
                                                WHERE a.item_id = nitem;
                                            ELSE
                                                UPDATE item a SET a.stock_$row = a.stock_$row - ( nqty * (SELECT packing FROM item_pack b WHERE b.item_id = nitem AND b.unit = nunit) )
                                                WHERE a.item_id = nitem;
                                            END IF;
                                        END IF;
                                       """
            
            sql1locedt  = sql1locedt + """
                                        IF nstocknum != ostocknum THEN
                                            IF adjust = 'IN' THEN
                                                IF ostocknum = $row THEN
                                                    UPDATE item a SET a.stock_$row = a.stock_$row - ( oqty * (SELECT packing FROM item_pack b WHERE b.item_id = oitem AND b.unit = ounit) )
                                                    WHERE a.item_id = oitem;
                                                END IF;

                                                IF nstocknum = $row THEN
                                                    UPDATE item a SET a.stock_$row = a.stock_$row + ( nqty * (SELECT packing FROM item_pack b WHERE b.item_id = nitem AND b.unit = nunit) )
                                                    WHERE a.item_id = nitem;
                                                END IF;
                                            ELSE
                                                IF ostocknum = $row THEN
                                                    UPDATE item a SET a.stock_$row = a.stock_$row + ( oqty * (SELECT packing FROM item_pack b WHERE b.item_id = oitem AND b.unit = ounit) )
                                                    WHERE a.item_id = oitem;
                                                END IF;

                                                IF nstocknum = $row THEN
                                                    UPDATE item a SET a.stock_$row = a.stock_$row - ( nqty * (SELECT packing FROM item_pack b WHERE b.item_id = nitem AND b.unit = nunit) )
                                                    WHERE a.item_id = nitem;
                                                END IF;
                                            END IF;
                                        ELSE
                                            IF nstocknum = $row THEN
                                                IF adjust = 'IN' THEN
                                                    IF nitem = oitem THEN
                                                        UPDATE item a SET a.stock_$row = a.stock_$row - ( oqty * (SELECT packing FROM item_pack b WHERE b.item_id = oitem AND b.unit = ounit) )
                                                                                                      + ( nqty * (SELECT packing FROM item_pack b WHERE b.item_id = nitem AND b.unit = nunit) )
                                                        WHERE a.item_id = nitem;
                                                    ELSE
                                                        UPDATE item a SET a.stock_$row = a.stock_$row - ( oqty * (SELECT packing FROM item_pack b WHERE b.item_id = oitem AND b.unit = ounit) )
                                                        WHERE a.item_id = oitem;

                                                        UPDATE item a SET a.stock_$row = a.stock_$row + ( nqty * (SELECT packing FROM item_pack b WHERE b.item_id = nitem AND b.unit = nunit) )
                                                        WHERE a.item_id = nitem;
                                                    END IF;
                                                ELSE
                                                    IF nitem = oitem THEN
                                                        UPDATE item a SET a.stock_$row = a.stock_$row + ( oqty * (SELECT packing FROM item_pack b WHERE b.item_id = oitem AND b.unit = ounit) )
                                                                                                      - ( nqty * (SELECT packing FROM item_pack b WHERE b.item_id = nitem AND b.unit = nunit) )
                                                        WHERE a.item_id = nitem;
                                                    ELSE
                                                        UPDATE item a SET a.stock_$row = a.stock_$row + ( oqty * (SELECT packing FROM item_pack b WHERE b.item_id = oitem AND b.unit = ounit) )
                                                        WHERE a.item_id = oitem;

                                                        UPDATE item a SET a.stock_$row = a.stock_$row - ( nqty * (SELECT packing FROM item_pack b WHERE b.item_id = nitem AND b.unit = nunit) )
                                                        WHERE a.item_id = nitem;
                                                    END IF;
                                                END IF;
                                            END IF;
                                        END IF;
                                       """
            
            sql1locdlt  = sql1locdlt + """
                                        IF stocknum = $row THEN
                                            IF adjust = 'IN' THEN
                                                UPDATE item a SET a.stock_$row = a.stock_$row - ( oqty * (SELECT packing FROM item_pack b WHERE b.item_id = oitem AND b.unit = ounit) )
                                                WHERE a.item_id = oitem;
                                            ELSE
                                                UPDATE item a SET a.stock_$row = a.stock_$row + ( oqty * (SELECT packing FROM item_pack b WHERE b.item_id = oitem AND b.unit = ounit) )
                                                WHERE a.item_id = oitem;
                                            END IF;
                                        END IF;
                                       """
        }
        
        def sql1drop = "DROP PROCEDURE IF EXISTS adjust_stock_add_proc"
        def sql1 = """
                    CREATE PROCEDURE adjust_stock_add_proc( IN stocknum varchar(50), IN nitem varchar(50), IN nqty DECIMAL(12,2), IN nunit varchar(50), IN adjust varchar(50) )
                    BEGIN
                   """
            sql1 = sql1 + sql1locadd
            sql1 = sql1 + """ 
                    END
                   """
        
        def sql2drop = "DROP PROCEDURE IF EXISTS adjust_stock_edit_proc"
        def sql2 = """
                    CREATE PROCEDURE adjust_stock_edit_proc( IN ostocknum varchar(50), IN nstocknum varchar(50), IN nitem varchar(50), IN nqty DECIMAL(12,2), IN nunit varchar(50), IN oitem varchar(50), IN oqty DECIMAL(12,2), IN ounit varchar(50), IN oadjust varchar(50), IN nadjust varchar(50), IN adjust varchar(50) )
                    BEGIN
                   """
            sql2 = sql2 + sql1locedt
            sql2 = sql2 + """ 
                    END
                   """
        
        def sql3drop = "DROP PROCEDURE IF EXISTS adjust_stock_delete_proc"
        def sql3 = """
                    CREATE PROCEDURE adjust_stock_delete_proc( IN stocknum varchar(50), IN oitem varchar(50), IN oqty DECIMAL(12,2), IN ounit varchar(50), IN adjust varchar(50) )
                    BEGIN
                   """
            sql3 = sql3 + sql1locdlt
            sql3 = sql3 + """ 
                    END
                   """
                   
        
        try{
            //def query = sessionfactory.execute(sql.toString())
            def datasource = grailsApplication.mainContext.getBean('dataSource')
            def conn = Sql.newInstance(datasource)
            
            try{
                conn.execute(sql1drop)
                conn.execute(sql1)
            }catch(Exception e){
                println e.toString()
            }
            
            try{
                conn.execute(sql2drop)
                conn.execute(sql2)
            }catch(Exception e){
                println e.toString()
            }
            
            try{
                conn.execute(sql3drop)
                conn.execute(sql3)
            }catch(Exception e){
                println e.toString()
            }
            
            conn.close()
        }catch(Exception e){
            println e.toString()
        }
    }
    
    def transferTriggers(){
        def sessionfactory = sessionFactory.currentSession
        
        def sql1drop = "DROP TRIGGER IF EXISTS transfer_stock_add"
        def sql1 = """
                    CREATE TRIGGER `transfer_stock_add` AFTER INSERT
                    ON `transfer_item`
                    FOR EACH ROW BEGIN
                        DECLARE stocknumfr NUMERIC;
                        DECLARE stocknumto NUMERIC;
                        SET stocknumfr = NEW.location_id_fr;
                        SET stocknumto = NEW.location_id_to;

                        CALL transfer_stock_add_proc( stocknumfr, stocknumto, NEW.item_id, NEW.qty, NEW.unit );
                    END
                   """
        
        def sql2drop = "DROP TRIGGER IF EXISTS transfer_stock_edit"
        def sql2 = """
                    CREATE TRIGGER `transfer_stock_edit` AFTER UPDATE
                    ON `transfer_item`
                    FOR EACH ROW BEGIN
                        DECLARE ostocknumfr NUMERIC;
                        DECLARE ostocknumto NUMERIC;
                        DECLARE nstocknumfr NUMERIC;
                        DECLARE nstocknumto NUMERIC;

                        SET ostocknumfr = OLD.location_id_fr;
                        SET ostocknumto = OLD.location_id_to;
                        SET nstocknumfr = NEW.location_id_fr;
                        SET nstocknumto = NEW.location_id_to;

                        CALL transfer_stock_edit_proc( ostocknumfr, ostocknumto, nstocknumfr, nstocknumto, NEW.item_id, NEW.qty, NEW.unit, OLD.item_id, OLD.qty, OLD.unit );
                    END
                   """
        
        def sql3drop = "DROP TRIGGER IF EXISTS transfer_stock_delete"
        def sql3 = """
                    CREATE TRIGGER `transfer_stock_delete` BEFORE DELETE
                    ON `transfer_item`
                    FOR EACH ROW BEGIN
                        DECLARE stocknumfr NUMERIC;
                        DECLARE stocknumto NUMERIC;
                        SET stocknumfr = OLD.location_id_fr;
                        SET stocknumto = OLD.location_id_to;

                        CALL transfer_stock_add_proc( stocknumfr, stocknumto, OLD.item_id, OLD.qty, OLD.unit );
                    END
                   """
       
        try{
            //def query = sessionfactory.execute(sql.toString())
            def datasource = grailsApplication.mainContext.getBean('dataSource')
            def conn = Sql.newInstance(datasource)
            try{
                conn.execute(sql1drop)
                conn.execute(sql1)
            }catch(Exception e){
                println e.toString()
            }
            try{
                conn.execute(sql2drop)
                conn.execute(sql2)
            }catch(Exception e){
                println e.toString()
            }
            try{
                conn.execute(sql3drop)
                conn.execute(sql3)
            }catch(Exception e){
                println e.toString()
            }
            conn.close()
        }catch(Exception e){
            println e.toString()
        }
    }
    
    def transferProcedures(){
        def sessionfactory = sessionFactory.currentSession
        
        def sql1locadd  = """"""
        def sql1locedt  = """"""
        def sql1locdlt  = """"""
        def sqllocation = "SELECT location_id FROM location"
        
        def query = sessionfactory.createSQLQuery(sqllocation)
        query.list().each{row->
            sql1locadd  = sql1locadd + """
                                        IF stocknumfr = $row THEN
                                            UPDATE item a SET a.stock_$row = a.stock_$row - ( nqty * (SELECT packing FROM item_pack b WHERE b.item_id = nitem AND b.unit = nunit) )
                                            WHERE a.item_id = nitem;
                                        END IF;

                                        IF stocknumto = $row THEN
                                            UPDATE item a SET a.stock_$row = a.stock_$row + ( nqty * (SELECT packing FROM item_pack b WHERE b.item_id = nitem AND b.unit = nunit) )
                                            WHERE a.item_id = nitem;
                                        END IF;
                                       """
            
            sql1locedt  = sql1locedt + """ 
                                        IF ostocknumfr = $row THEN
                                            UPDATE item a SET a.stock_$row = a.stock_$row + ( oqty * (SELECT packing FROM item_pack b WHERE b.item_id = oitem AND b.unit = ounit) )
                                            WHERE a.item_id = oitem;
                                        END IF;

                                        IF ostocknumto = $row THEN
                                            UPDATE item a SET a.stock_$row = a.stock_$row - ( oqty * (SELECT packing FROM item_pack b WHERE b.item_id = oitem AND b.unit = ounit) )
                                            WHERE a.item_id = oitem;
                                        END IF;

                                        IF nstocknumfr = $row THEN
                                            UPDATE item a SET a.stock_$row = a.stock_$row - ( nqty * (SELECT packing FROM item_pack b WHERE b.item_id = nitem AND b.unit = nunit) )
                                            WHERE a.item_id = nitem;
                                        END IF;

                                        IF nstocknumto = $row THEN
                                            UPDATE item a SET a.stock_$row = a.stock_$row + ( nqty * (SELECT packing FROM item_pack b WHERE b.item_id = nitem AND b.unit = nunit) )
                                            WHERE a.item_id = nitem;
                                        END IF;
                                       """
            
            sql1locdlt  = sql1locdlt + """
                                        IF stocknumfr = $row THEN
                                            UPDATE item a SET a.stock_$row = a.stock_$row + ( oqty * (SELECT packing FROM item_pack b WHERE b.item_id = oitem AND b.unit = ounit) )
                                            WHERE a.item_id = oitem;
                                        END IF;

                                        IF stocknumto = $row THEN
                                            UPDATE item a SET a.stock_$row = a.stock_$row - ( oqty * (SELECT packing FROM item_pack b WHERE b.item_id = oitem AND b.unit = ounit) )
                                            WHERE a.item_id = oitem;
                                        END IF;
                                       """
        }
        
        def sql1drop = "DROP PROCEDURE IF EXISTS transfer_stock_add_proc"
        def sql1 = """
                    CREATE PROCEDURE transfer_stock_add_proc( IN stocknumfr varchar(50), IN stocknumto varchar(50), IN nitem varchar(50), IN nqty DECIMAL(12,2), IN nunit varchar(50) )
                    BEGIN
                   """
            sql1 = sql1 + sql1locadd
            sql1 = sql1 + """ 
                    END
                   """

        def sql2drop = "DROP PROCEDURE IF EXISTS transfer_stock_edit_proc"
        def sql2 = """
                    CREATE PROCEDURE transfer_stock_edit_proc( IN ostocknumfr varchar(50), IN ostocknumto varchar(50), IN nstocknumfr varchar(50), IN nstocknumto varchar(50), IN nitem varchar(50), IN nqty DECIMAL(12,2), IN nunit varchar(50), IN oitem varchar(50), IN oqty DECIMAL(12,2), IN ounit varchar(50) )
                    BEGIN
                   """
            sql2 = sql2 + sql1locedt
            sql2 = sql2 + """ 
                    END
                   """
        
        def sql3drop = "DROP PROCEDURE IF EXISTS transfer_stock_delete_proc"
        def sql3 = """
                    CREATE PROCEDURE transfer_stock_delete_proc( IN stocknumfr varchar(50), IN stocknumto varchar(50), IN oitem varchar(50), IN oqty DECIMAL(12,2), IN ounit varchar(50) )
                    BEGIN
                   """
            sql3 = sql3 + sql1locdlt
            sql3 = sql3 + """ 
                    END
                   """
                   
        
        try{
            //def query = sessionfactory.execute(sql.toString())
            def datasource = grailsApplication.mainContext.getBean('dataSource')
            def conn = Sql.newInstance(datasource)
            
            try{
                conn.execute(sql1drop)
                conn.execute(sql1)
            }catch(Exception e){
                println e.toString()
            }
            
            try{
                conn.execute(sql2drop)
                conn.execute(sql2)
            }catch(Exception e){
                println e.toString()
            }
            
            try{
                conn.execute(sql3drop)
                conn.execute(sql3)
            }catch(Exception e){
                println e.toString()
            }
            
            conn.close()
        }catch(Exception e){
            println e.toString()
        }
    }
}
