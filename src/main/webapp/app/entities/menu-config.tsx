import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const ConfigMenuList = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/config">
        Config
      </MenuItem>
      <MenuItem icon="asterisk" to="/config-log">
        Config Log
      </MenuItem>
      <MenuItem icon="asterisk" to="/emp-group">
        Emp Group
      </MenuItem>
      <MenuItem icon="asterisk" to="/employee">
        Employee
      </MenuItem>
      <MenuItem icon="asterisk" to="/login-history">
        Login History
      </MenuItem>
      <MenuItem icon="asterisk" to="/role-group">
        Role Group
      </MenuItem>
      <MenuItem icon="asterisk" to="/roles">
        Roles
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default ConfigMenuList as React.ComponentType<any>;
