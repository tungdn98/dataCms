import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RoleGroup from './role-group';
import RoleGroupDetail from './role-group-detail';
import RoleGroupUpdate from './role-group-update';
import RoleGroupDeleteDialog from './role-group-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RoleGroupUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RoleGroupUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RoleGroupDetail} />
      <ErrorBoundaryRoute path={match.url} component={RoleGroup} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RoleGroupDeleteDialog} />
  </>
);

export default Routes;
