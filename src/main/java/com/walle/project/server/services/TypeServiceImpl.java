package com.walle.project.server.services;

import com.walle.project.server.entity.Type;
import com.walle.project.server.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeRepository typeRepository;

    @Override
    public Type getById(Long id) {
        Type type = typeRepository.getById (id);
        return type;
    }

    @Override
    public List <Type> getAll() {
        List <Type> types = typeRepository.getAllBy ( );
        return types;
    }

    @Override
    public void saveOrUpdate(Type type) {
        typeRepository.save (type);
    }

    @Override
    public void deleteById(Long id) {
        typeRepository.deleteById (id);
    }

    @Override
    public void saveOrUpdateAll(List <Type> types) {
        typeRepository.saveAll (types);
    }
}
