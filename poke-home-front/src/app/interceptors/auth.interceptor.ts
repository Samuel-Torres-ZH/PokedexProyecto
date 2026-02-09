import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  // Clonamos la petición para agregarle las credenciales automáticamente
  const authReq = req.clone({
    withCredentials: true
  });
  return next(authReq);
};