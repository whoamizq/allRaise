package com.whoami.raise.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whoami.raise.mapper.TypePOMapper;
import com.whoami.raise.service.TypeService;

@Service
@Transactional(readOnly = true)
public class TypeServiceImpl implements TypeService{

	@Autowired
	private TypePOMapper typePOMapper;
}
