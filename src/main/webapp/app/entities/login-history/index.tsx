import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import LoginHistory from './login-history';
import LoginHistoryDetail from './login-history-detail';
import LoginHistoryUpdate from './login-history-update';
import LoginHistoryDeleteDialog from './login-history-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LoginHistoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LoginHistoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LoginHistoryDetail} />
      <ErrorBoundaryRoute path={match.url} component={LoginHistory} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={LoginHistoryDeleteDialog} />
  </>
);

export default Routes;
