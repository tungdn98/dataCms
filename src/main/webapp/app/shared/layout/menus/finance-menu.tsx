import React from 'react';

import { NavDropdown } from './menu-components';
import EntitiesMenuItems from 'app/entities/menu';
import FinanceMenuList from 'app/entities/menu-finance';

export const FinanceMenu = props => (
  <NavDropdown icon="th-list" name="finance" id="entity-menu" data-cy="entity" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <FinanceMenuList />
  </NavDropdown>
);
