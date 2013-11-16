/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sumservice;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Kalle
 */
public class SumRepository {
    
    private ArrayList<SumSlot> repository;
    public SlotLock lock;
    
    public SumRepository()
    {
        repository = new ArrayList<SumSlot>();
        lock = new SlotLock();
    }
    
    public SumSlot AddSlot(){
        SumSlot slot = new SumSlot(lock);
        repository.add(slot);
        return slot;
    }
    
    public int GetTotalSum(){
        lock.lock();
        int total_sum = 0;
        for (SumSlot slot : repository){
            total_sum += slot.GetSum();
        }
        lock.unlock();
        return total_sum;
    }
    
    public int GetMaxSum(){
        lock.lock();
        int max_sum = 0;
        int n = 0;
        int max = 0;
        for (SumSlot slot : repository){
            int tmp = slot.GetSum();
            if (max_sum < tmp){
                max_sum = tmp;
                max = n;
            }
            ++n;
        }
        lock.unlock();
        return max+1;
    }
    
     public int GetTotalN(){
         lock.lock();
        int total_n = 0;
        for (SumSlot slot : repository){
            total_n += slot.GetN();
        }
        lock.unlock();
        return total_n;
    }
    
}
