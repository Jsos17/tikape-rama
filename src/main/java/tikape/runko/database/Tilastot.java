/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.SQLException;
import java.util.HashMap;
import tikape.runko.domain.SmoothieRaakaAine;

/**
 *
 * @author jpssilve
 */
public class Tilastot extends SmoothieRaakaAineDao implements Dao<SmoothieRaakaAine, Integer>{
    
    private HashMap<Integer, SmoothieRaakaAine> raavain;
    
    public Tilastot(Database database) {
        super(database);
        this.raavain=new HashMap<>();
    }
    
    @Override
    public SmoothieRaakaAine findOne(Integer key) throws SQLException{
        if (!raavain.containsKey(key)){
            SmoothieRaakaAine aine= super.findOne(key);
            raavain.put(key, aine);
        }
        
        
        return raavain.get(key);
    }
    
    
}
