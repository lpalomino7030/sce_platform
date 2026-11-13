import { ApplicationConfig, provideBrowserGlobalErrorListeners, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {provideHttpClient} from '@angular/common/http';
import { withInterceptors } from '@angular/common/http';
import { authInterceptor } from './core/interceptors/auth.interceptor';
import { provideIcons } from '@ng-icons/core';
import {
  lucideFileText,
  lucideSend,
  lucideHandshake,lucideTable2,lucideSquareKanban,
  lucideEllipsisVertical,lucideBuilding2,lucideCalendar,lucideCircleX,lucidePlus,lucideCircleCheckBig

} from '@ng-icons/lucide';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideIcons({
      lucideFileText,
      lucideSend,
      lucideHandshake,lucideTable2,lucideSquareKanban,
      lucideEllipsisVertical,lucideBuilding2,lucideCalendar,lucideCircleX,lucidePlus,lucideCircleCheckBig
    }),
    provideHttpClient( withInterceptors([authInterceptor]))
  ]
};
