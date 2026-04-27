export interface ApiResponse<T> {
  fecha: Date;
  status: number;
  message: string;
  data: T;
}
