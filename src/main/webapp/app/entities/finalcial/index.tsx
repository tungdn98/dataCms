import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Finalcial from './finalcial';
import FinalcialDetail from './finalcial-detail';
import FinalcialUpdate from './finalcial-update';
import FinalcialDeleteDialog from './finalcial-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FinalcialUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FinalcialUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FinalcialDetail} />
      <ErrorBoundaryRoute path={match.url} component={Finalcial} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FinalcialDeleteDialog} />
  </>
);

export default Routes;
