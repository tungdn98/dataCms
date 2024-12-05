import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmpGroup from './emp-group';
import EmpGroupDetail from './emp-group-detail';
import EmpGroupUpdate from './emp-group-update';
import EmpGroupDeleteDialog from './emp-group-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmpGroupUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmpGroupUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmpGroupDetail} />
      <ErrorBoundaryRoute path={match.url} component={EmpGroup} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EmpGroupDeleteDialog} />
  </>
);

export default Routes;
