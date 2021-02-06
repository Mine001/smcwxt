package com.hulqframe.cwgl.service;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.base.BaseService;
import com.hulqframe.cwgl.mapper.ProjectMapper;
import com.hulqframe.cwgl.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService extends BaseService<Project,Integer> {
@Autowired
private ProjectMapper projectMapper;
@Override
public BaseMapper<Project, Integer> getBaseMapper() {
return projectMapper;
}
}
