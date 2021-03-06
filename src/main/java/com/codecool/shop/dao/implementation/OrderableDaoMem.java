package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.OrderableDao;
import com.codecool.shop.model.Orderable;

import java.util.ArrayList;
import java.util.List;

public class OrderableDaoMem implements OrderableDao {

    private List<Orderable> DATA = new ArrayList<>();
    private static OrderableDaoMem instance = null;

    private OrderableDaoMem() {
    }

    public static OrderableDaoMem getInstance() {
        if (instance == null) {
            instance = new OrderableDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Orderable category) {
        category.setId(DATA.size() + 1);
        DATA.add(category);
    }

    @Override
    public Orderable find(int id) {if(id<0){
        throw new IllegalArgumentException("ID must equal or bigger than 0");
    }
        return DATA.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        if(id<0)
        {
            throw new IllegalArgumentException("ID must equal or bigger than 0");
        }
        DATA.remove(find(id));
    }

    @Override
    public List<Orderable> getAll() {
        return DATA;
    }

    public boolean order(){
        System.out.println("ordered");
        return true;
    }
}