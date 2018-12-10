import jenkins.model.*
import hudson.model.*

Jenkins.instance.setNumExecutors(2)

hudson = hudson.model.Hudson.instance
hudson.slaves.each { slave -> 
  print "Slave  $slave.nodeName : Labels: $slave.labelString"
  slave.labelString = slave.labelString + " " + "docker"
  println "   --> New labels: $slave.labelString"
}
hudson.save()