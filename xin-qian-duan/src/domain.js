//全局的配置的一些服务端的域名
const serverpath='http://127.0.0.1:8888/'
//图片上传
const serverimageupload='http://'
//部门
const bumen=[
  {label:'人事部',value:'1'},
  {label:'市场部',value:'2'},
  {label:'研发部',value:'3'}
]

//试题类型
const shititype=[
  {label:'单选题',value:'1'},
  {label:'多选题',value:'2'},
  {label:'判断题',value:'3'},
  {label:'填空题',value:'4'},
  {label:'问答题',value:'5'}];

//试题难易程度
const texts=["简单","一般","稍难","困难","汗颜难"];
const textss=[
  {label:'简单',value:'1'},
  {label:'一般',value:'2'},
  {label:'稍难',value:'3'},
  {label:'困难',value:'4'},
  {label:'汗颜难',value:'5'}];

//试题状态
const shitistatus=[
  {label:'开放',value:'1'},
  {label:'关闭',value:'0'}
]

//选项名称
const xuanxiang=["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"]

const userinfo={};
export default {
  serverpath,
  userinfo,
  shititype,
  texts,
  textss,
  shitistatus,
  xuanxiang,
  bumen,

}

