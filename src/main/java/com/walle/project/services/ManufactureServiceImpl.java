package com.walle.project.services;

import com.walle.project.entity.Manufacture;
import com.walle.project.repository.ManufactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ManufactureServiceImpl implements ManufactureServices{
    @Autowired
    private ManufactureRepository manufactureRepository;
    @Override
    public Manufacture getById(Long id) {
        Manufacture manufacture = manufactureRepository.getById (id);
        return manufacture;
    }

    @Override
    public List<Manufacture> getAll() {
        List<Manufacture> manufactures = manufactureRepository.getAllBy ();
        return manufactures;
    }
    @Override
    public void saveOrUpdate(Manufacture manufacture) {
        manufactureRepository.save (manufacture);

    }
    @Override
    public void deleteByID(Long id) {
        manufactureRepository.deleteById (id);

    }
    @Override
    public void saveOrUpdateAll(List <Manufacture> manufactures) {
        manufactureRepository.saveAll (manufactures);
    }
}
