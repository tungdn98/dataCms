import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ConfigLog from './config-log';
import ConfigLogDetail from './config-log-detail';
import ConfigLogUpdate from './config-log-update';
import ConfigLogDeleteDialog from './config-log-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ConfigLogUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ConfigLogUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ConfigLogDetail} />
      <ErrorBoundaryRoute path={match.url} component={ConfigLog} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ConfigLogDeleteDialog} />
  </>
);

export default Routes;
