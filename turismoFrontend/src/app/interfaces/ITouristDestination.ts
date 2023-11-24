
export interface ITouristDestination{
    id:number,
    name:String,
    city:{
        id:Number
        name:String,
        departmentId:Number
    }
}