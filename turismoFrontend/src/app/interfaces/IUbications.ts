type country = {
    name:String,
    id:number
}

type city = {
    name:String,
    id:number,
   departmentId:number
}
type department = {
    name:String,
    id:number,
   countryId:number
}


export interface IUbications{
    countries:Array<country>,
    departments:Array<department>,
    cities:Array<city>
}