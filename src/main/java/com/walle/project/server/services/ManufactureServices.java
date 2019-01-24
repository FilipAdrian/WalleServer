package com.walle.project.server.services;

import com.walle.project.server.entity.Manufacture;

import java.util.List;

public interface ManufactureServices {
    Manufacture getById(Long id);

    List<Manufacture> getAll();

    void saveOrUpdate(Manufacture manufacture);

    void deleteByID(Long id);

    void saveOrUpdateAll(List <Manufacture> manufactures);
}
