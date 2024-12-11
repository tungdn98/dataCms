import React from 'react';

import { NavDropdown } from './menu-components';
import EntitiesMenuItems from 'app/entities/menu';
import ProjectManagmentMenuList from 'app/entities/menu-project-managment';


export const ProjectManagmentMenu = props => (
  <NavDropdown icon="th-list" name="PM" id="entity-menu" data-cy="entity" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <ProjectManagmentMenuList />
  </NavDropdown>
);
