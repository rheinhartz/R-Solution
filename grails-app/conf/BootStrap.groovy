import com.r.domain.ShiroUser
import com.r.domain.ShiroRole
import com.r.service.DatabaseService

import org.apache.shiro.crypto.hash.Sha256Hash

class BootStrap {

    def DatabaseService
    
    def init = { servletContext ->
        
        def username = "admin"
        def password = new Sha256Hash("admin").toHex()
        def fname    = "Juan"
        def mname    = "Dela"
        def lname    = "Cruz"
        
        def user = ShiroUser.findAll()
        def role = ShiroRole.findAll()
        
        if(!role){
            role = new ShiroRole(name: "System Administrator")
            role.addToPermissions("*:*")
            //role.save()
        }

        if(!user){
            
            user = new ShiroUser(username: username, password: password, fname: fname, mname: mname, lname: lname, isactive: 1, isdeleted: 0)
            //user.addToRoles(role)
            user.addToPermissions("*:*")
            user.save()
            
            // Devs
            username = "rheinhart"
            password = new Sha256Hash("admin").toHex()
            fname    = "Richard Eric"
            mname    = "France"
            lname    = "Trijo"
            
            user = new ShiroUser(username: username, password: password, fname: fname, mname: mname, lname: lname, isactive: 1, isdeleted: 0)
            user.addToPermissions("*:*")
            user.save()
            
            username = "wrecklez"
            password = new Sha256Hash("admin").toHex()
            fname    = "Carlos Bernardo"
            mname    = "Benardo"
            lname    = "Co"
            
            user = new ShiroUser(username: username, password: password, fname: fname, mname: mname, lname: lname, isactive: 1, isdeleted: 0)
            user.addToPermissions("*:*")
            user.save()
            
        }
        
        databaseService.createDatabase()
        
        databaseService.itemTriggers()
        databaseService.locationTriggers()
        
        databaseService.physicalTriggers()
        databaseService.physicalProcedures()
        
        databaseService.withdrawalTriggers()
        databaseService.withdrawalProcedures()
        
        databaseService.receivingTriggers()
        databaseService.receivingProcedures()
        
        databaseService.adjustTriggers()
        databaseService.adjustProcedures()
        
        databaseService.transferTriggers()
        databaseService.transferProcedures()
        
        /*
            DELIMITER $$
            CREATE TRIGGER `physical_stock_add` AFTER INSERT
                ON `physical_item`
                FOR EACH ROW BEGIN

                        UPDATE item a SET a.stock = a.stock + ( NEW.qty * (SELECT packing FROM item_pack b WHERE b.item_id = NEW.item_id AND b.unit = NEW.unit) )
                        WHERE a.item_id = NEW.item_id;

                END$$
            DELIMITER ;

            
            DELIMITER $$
            CREATE
                TRIGGER `physical_stock_edit` AFTER UPDATE
                ON `physical_item`
                FOR EACH ROW BEGIN
                    UPDATE item a SET a.stock = a.stock - ( OLD.qty * (SELECT packing FROM item_pack b WHERE b.item_id = OLD.item_id AND b.unit = OLD.unit) )
                                                        + ( NEW.qty * (SELECT packing FROM item_pack b WHERE b.item_id = NEW.item_id AND b.unit = NEW.unit) )
                    WHERE a.item_id = NEW.item_id;
                END$$
            DELIMITER ;
            
        
            DELIMITER $$
            CREATE
                TRIGGER `physical_stock_delete` BEFORE DELETE
                ON `physical_item`
                FOR EACH ROW BEGIN
                    UPDATE item a SET a.stock = a.stock - ( OLD.qty * (SELECT packing FROM item_pack b WHERE b.item_id = OLD.item_id AND b.unit = OLD.unit) )
                    WHERE a.item_id = OLD.item_id;
                END$$
            DELIMITER ;
        */
        
        /*
        username = "juan"
        password = new Sha256Hash("juan").toHex()
        fname    = "Juan"
        mname    = "Dela"
        lname    = "Cruz"
        
        role = new ShiroRole(name: "Encoder")
        role.addToPermissions("home:*")
        role.addToPermissions("item:*")
        role.save()
        
        user = new ShiroUser(username: username, password: password, fname: fname, mname: mname, lname: lname, isactive: 1, isdeleted: 0)
        user.addToRoles(role)
        user.save()
        */
    }
    
    
    def destroy = {
    }
}