import hacking.OrgUser
import hacking.Organization
import hacking.Role
import hacking.User
import hacking.UserRole

class BootStrap {

	def init = {
		def adminRole = Role.findByAuthority('ROLE_ADMIN') ?: new Role('ROLE_ADMIN').save(failOnError: true)
		def userRole = Role.findByAuthority('ROLE_USER') ?: new Role('ROLE_USER').save(failOnError: true)

		def org1 = Organization.findByName('Org1') ?: new Organization('Org1').save(failOnError: true)
		def org2 = Organization.findByName('Org2') ?: new Organization('Org2').save(failOnError: true)

		if (!User.count()) {
			def admin = new User('admin', 'password').save(failOnError: true)
			new OrgUser(org1, admin).save(failOnError: true)
			UserRole.create admin, adminRole

			def user = new User('user', 'password').save(failOnError: true)
			new OrgUser(org2, user).save(failOnError: true)
			UserRole.create user, userRole

			def disabledUser = new User(username: 'disabled', password: 'password', enabled: false).save(failOnError: true)
			new OrgUser(org1, disabledUser).save(failOnError: true)
			UserRole.create disabledUser, userRole, true
		}
	}
}
