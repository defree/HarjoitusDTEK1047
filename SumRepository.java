/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sumservice.HarjoitusDTEK1047;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Kalle, Esa
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
    
    public synchronized int GetTotalSum(){
        lock.lock();
        int total_sum = 0;
        for (SumSlot slot : repository){
            int sum = slot.GetSum();
            //System.out.println(sum);
            total_sum += sum;
        }
        //lock.unlock();
        return total_sum;
    }
    
    public synchronized int GetMaxSum(){

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
        System.out.println("MaxSum:"+ max_sum);
        //lock.unlock();
        return max+1;
    }
    
     public synchronized int GetTotalN(){

         lock.lock();
        int total_n = 0;
        for (SumSlot slot : repository){
            total_n += slot.GetN();
        }
        //lock.unlock();
        return total_n;
    }
    
}
