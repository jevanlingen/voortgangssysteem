import scala.collection.mutable.HashMap

object Utils {
  def getMapOfProjectManagers(projectManagerList:List[API_models.Projectmanager]):HashMap[String, String] = {
    val projectManagers = new HashMap[String, String]
    
    for (i <- 0 until projectManagerList.size ) {
      projectManagers += projectManagerList(i).getId().toString() -> projectManagerList(i).getName()
    }
    
    return projectManagers
  }
}