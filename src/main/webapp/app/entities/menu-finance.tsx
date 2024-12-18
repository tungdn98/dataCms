import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const FinanceMenuList = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/finalcial">
        Finalcial
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default FinanceMenuList as React.ComponentType<any>;
