import React from 'react';

import { NavDropdown } from './menu-components';
import EntitiesMenuItems from 'app/entities/menu';
import ConfigMenuList from 'app/entities/menu-config';

export const ConfigMenu = props => (
  <NavDropdown icon="th-list" name="Config" id="entity-menu" data-cy="entity" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <ConfigMenuList />
  </NavDropdown>
);
