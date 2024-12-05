import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ActivityType from './activity-type';
import ActivityTypeDetail from './activity-type-detail';
import ActivityTypeUpdate from './activity-type-update';
import ActivityTypeDeleteDialog from './activity-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ActivityTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ActivityTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ActivityTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={ActivityType} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ActivityTypeDeleteDialog} />
  </>
);

export default Routes;
