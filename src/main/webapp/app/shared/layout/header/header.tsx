import './header.scss';

import React, { useState } from 'react';
import { Navbar, Nav, NavbarToggler, Collapse, Offcanvas, OffcanvasHeader, OffcanvasBody } from 'reactstrap';
import LoadingBar from 'react-redux-loading-bar';

import { Home, Brand } from './header-components';
import { AdminMenu, EntitiesMenu, AccountMenu } from '../menus';
import { ConfigMenu } from 'app/shared/layout/menus/config-menu';
import { FinanceMenu } from 'app/shared/layout/menus/finance-menu';
import { ProjectManagmentMenu } from 'app/shared/layout/menus/project-management-menu';

export interface IHeaderProps {
  isAuthenticated: boolean;
  isAdmin: boolean;
  ribbonEnv: string;
  isInProduction: boolean;
  isOpenAPIEnabled: boolean;
}

const Header = (props: IHeaderProps) => {
  const [offcanvasOpen, setOffcanvasOpen] = useState(false);

  const toggleOffcanvas = () => setOffcanvasOpen(!offcanvasOpen);

  const closeOffcanvas = () => setOffcanvasOpen(false); // Hàm đóng offcanvas

  return (
    <div id="app-header">
      <LoadingBar className="loading-bar" />
      <Navbar data-cy="navbar" dark expand="md" fixed="top" className="jh-navbar">
        <NavbarToggler aria-label="Menu Hệ thống" onClick={toggleOffcanvas} />
        <div className="app-name">DATA CMS</div>

        <Offcanvas isOpen={offcanvasOpen} direction="start" toggle={toggleOffcanvas}>
          <OffcanvasHeader toggle={toggleOffcanvas}>Menu</OffcanvasHeader>
          <OffcanvasBody>
            <Nav id="header-tabs" className="flex-column" navbar>
              {' '}
              {/* Thêm onClick */}
              <Home />
              {props.isAuthenticated && <EntitiesMenu />}
              {props.isAuthenticated && props.isAdmin && <FinanceMenu showOpenAPI={props.isOpenAPIEnabled} />}
              {props.isAuthenticated && props.isAdmin && <ProjectManagmentMenu showOpenAPI={props.isOpenAPIEnabled} />}
              {props.isAuthenticated && props.isAdmin && <ConfigMenu showOpenAPI={props.isOpenAPIEnabled} />}
              {props.isAuthenticated && props.isAdmin && <AdminMenu showOpenAPI={props.isOpenAPIEnabled} />}
              <AccountMenu isAuthenticated={props.isAuthenticated} />
            </Nav>
          </OffcanvasBody>
        </Offcanvas>
      </Navbar>
    </div>
  );
};

export default Header;
