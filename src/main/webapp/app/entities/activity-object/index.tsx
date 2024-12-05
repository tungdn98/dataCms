import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ActivityObject from './activity-object';
import ActivityObjectDetail from './activity-object-detail';
import ActivityObjectUpdate from './activity-object-update';
import ActivityObjectDeleteDialog from './activity-object-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ActivityObjectUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ActivityObjectUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ActivityObjectDetail} />
      <ErrorBoundaryRoute path={match.url} component={ActivityObject} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ActivityObjectDeleteDialog} />
  </>
);

export default Routes;
