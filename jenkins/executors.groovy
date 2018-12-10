import jenkins.model.*
import hudson.model.*

Jenkins.instance.setNumExecutors(5)

hudson = Hudson.instance
hudson.slaves.findAll { it.nodeName.equals("slave4") }.each { slave -> 
  print "Slave  $slave.nodeName : Labels: $slave.labelString"
  slave.labelString = slave.labelString + " " + "offline"
  println "   --> New labels: $slave.labelString"
}
hudson.save()